package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.DTO.KhachHangDTO;
import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/ban-hang")
public class BanHangTaiQuayController {

    @Autowired
    private SanPhamRepo sanPhamRepo;

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonCTRepo;

    @Autowired
    private KhachHangRepo khachHangRepo;

    @Autowired
    private HinhThucThanhToanRepo thanhToanRepo;

    @Autowired
    private MauSacRepo mauSacRepo;

    @Autowired
    private KichThuocRepo kichThuocRepo;
    @Autowired
    private DanhMucRepo danhMucRepo;

    @Autowired
    private GiamGiaRepo giamGiaRepo;

    @Autowired
    private NhanVienRepo nhanVienRepo;

    @GetMapping("/hien-thi")
    public String hienThiBanHang(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "danhMuc", required = false) Integer danhMucId,
                                 @RequestParam(value = "mauSac", required = false) Integer mauSac,
                                 @RequestParam(value = "kichThuoc", required = false) Integer kichThuoc,
                                 Model model, HttpSession session) {

        List<SanPham> danhSachSanPham;
// ✅ Nếu quay lại sau khi thanh toán thì không có giỏ hàng nữa
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("gioHang");

        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("thongBao", "🛒 Giỏ hàng đang trống. Vui lòng chọn sản phẩm mới để bán.");
        }

        // Lấy Map<Integer, CartItem> từ Cart
        Map<Integer, CartItem> gioHang = cart != null ? cart.getItems() : new HashMap<>();
        model.addAttribute("gioHang", gioHang);

        boolean coLoc = (keyword != null && !keyword.isEmpty())
                || danhMucId != null
                || mauSac != null
                || kichThuoc != null;

        if (coLoc) {
            danhSachSanPham = sanPhamRepo.timKiemSanPhamNangCao(keyword, danhMucId, mauSac, kichThuoc);
        } else {
            danhSachSanPham = sanPhamRepo.findAll();
        }
        List<HinhThucThanhToan> hinhThucs = thanhToanRepo.findAll();
        // ✅ Chỉ lấy phiếu giảm giá đang hoạt động (trangThai = 1, trong thời gian hiệu lực)
        List<GiamGia> phieuGiamGias = giamGiaRepo.findMaGiamGiaConHieuLuc(LocalDateTime.now());
        model.addAttribute("danhSachSanPham", danhSachSanPham);
        // 🔥 Phải luôn có dòng này để modal khách hàng hoạt động
        model.addAttribute("khachHangs", khachHangRepo.findAll());

        model.addAttribute("hinhThucs", thanhToanRepo.findAll());
        model.addAttribute("danhMucs", danhMucRepo.findAll());
        model.addAttribute("listMau", mauSacRepo.findAll());
        model.addAttribute("listSize", kichThuocRepo.findAll());
        model.addAttribute("phieuGiamGias", phieuGiamGias); // 👈 Gửi danh sách phiếu giảm giá sang view
        // Giữ lại giá trị lọc
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDanhMuc", danhMucId);
        model.addAttribute("selectedMau", mauSac);
        model.addAttribute("selectedSize", kichThuoc);

        return "ViewBanHang/ban-hang";
    }


    @PostMapping("/thanh-toan")
    public String xuLyThanhToan(@RequestParam Map<String, String> params,
                                HttpSession session,
                                Model model,
                                RedirectAttributes redirect) {

        // 1. Kiểm tra đăng nhập
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("adminDangNhap");
        if (taiKhoan == null || taiKhoan.getLoaiTaiKhoan() != 1) {
            return "redirect:/quan-ao/login";

        }

        NhanVien nhanVien = taiKhoan.getNhanVien();

        // 2. Lấy thông tin khách hàng và hình thức thanh toán
        int idKhach = Integer.parseInt(params.get("khachHang"));
        int idHinhThuc = Integer.parseInt(params.get("hinhThuc"));
        int tongTienGoc = Integer.parseInt(params.get("tongTien"));
        int tongTienSauGiam = tongTienGoc;

        KhachHang khachHang = khachHangRepo.findById(idKhach).orElse(null);
        HinhThucThanhToan hinhThuc = thanhToanRepo.findById(idHinhThuc).orElse(null);

        // 3. Xử lý giảm giá nếu có
        GiamGia giamGia = null;
        String giamGiaStr = params.get("giamGiaId");
        if (giamGiaStr != null && !giamGiaStr.isEmpty()) {
            int idGiamGia = Integer.parseInt(giamGiaStr);
            giamGia = giamGiaRepo.findById(idGiamGia).orElse(null);

            if (giamGia != null && giamGia.getSoLuong() > 0
                    && LocalDateTime.now().isAfter(giamGia.getNgayBatDau())
                    && LocalDateTime.now().isBefore(giamGia.getNgayKetThuc())) {

                Integer loai = giamGia.getLoaiGiamGia();
                if (loai != null && loai == 1) {
                    // Giảm theo phần trăm
                    tongTienSauGiam = tongTienGoc - (tongTienGoc * giamGia.getGiaTriGiam()) / 100;
                } else {
                    // Giảm theo tiền mặt
                    tongTienSauGiam = tongTienGoc - giamGia.getGiaTriGiam();
                }


                if (tongTienSauGiam < 0) tongTienSauGiam = 0;

                // Trừ số lượng phiếu còn lại
                giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                giamGiaRepo.save(giamGia);
            }
        }
        String tienKhachStr = params.get("tienKhachDua");
        BigDecimal tienKhachDua = BigDecimal.ZERO;
        BigDecimal tienTraLai = BigDecimal.ZERO;

        if (tienKhachStr != null && !tienKhachStr.isEmpty()) {
            tienKhachDua = new BigDecimal(tienKhachStr);
            tienTraLai = tienKhachDua.subtract(BigDecimal.valueOf(tongTienSauGiam));
            if (tienTraLai.compareTo(BigDecimal.ZERO) < 0) tienTraLai = BigDecimal.ZERO;
        }
        // 4. Kiểm tra tồn kho các sản phẩm
        List<String> loiSanPham = new ArrayList<>();
        Map<Integer, Integer> gioHang = new HashMap<>();

        for (String key : params.keySet()) {
            if (key.startsWith("sp_")) {
                String rawValue = params.get(key);
                if (rawValue == null || rawValue.isEmpty()) continue;

                Integer idSp = Integer.parseInt(key.substring(3));
                int soLuong = Integer.parseInt(rawValue);

                SanPham sp = sanPhamRepo.findById(idSp).orElse(null);
                if (sp != null && soLuong > 0) {
                    if (soLuong > sp.getSoLuong()) {
                        loiSanPham.add("❌ Sản phẩm '" + sp.getTenSanPham() + "' chỉ còn " + sp.getSoLuong() + " cái.");
                    } else {
                        gioHang.put(idSp, soLuong);
                    }
                }
            }
        }

        if (!loiSanPham.isEmpty()) {
            redirect.addFlashAttribute("error", "Không thể thanh toán:\n" + String.join("\n", loiSanPham));
            return "redirect:/ban-hang/hien-thi";
        }

        // 5. Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHd("HD" + System.currentTimeMillis());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTrangThai(1);
        hoaDon.setTongTien(tongTienSauGiam);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setHinhThucThanhToan(hinhThuc);
        hoaDon.setGiamGia(giamGia);
        hoaDon.setTrangThaiDonHang(TrangThaiDonHang.DA_HOAN_THANH);
        hoaDonRepo.save(hoaDon);

        // 6. Lưu chi tiết hóa đơn + trừ hàng tồn
        for (Map.Entry<Integer, Integer> entry : gioHang.entrySet()) {
            Integer idSp = entry.getKey();
            Integer soLuong = entry.getValue();

            SanPham sp = sanPhamRepo.findById(idSp).orElse(null);
            if (sp != null) {
                HoaDonChiTiet ct = new HoaDonChiTiet();
                ct.setHoaDon(hoaDon);
                ct.setSanPham(sp);
                ct.setSoLuong(soLuong);
                BigDecimal donGia = sp.getGia();
                BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
                ct.setDonGia(donGia);
                ct.setThanhTien(thanhTien);
                ct.setMaHdct("CT" + System.nanoTime());
                ct.setTrangThai(1);
                hoaDonCTRepo.save(ct);

                sp.setSoLuong(sp.getSoLuong() - soLuong);
                sanPhamRepo.save(sp);
            }
        }

        // 7. QR nếu là chuyển khoản hoặc ví
        if (hinhThuc != null && (
                hinhThuc.getTenHinhThuc().equalsIgnoreCase("Chuyển khoản") ||
                        hinhThuc.getTenHinhThuc().equalsIgnoreCase("Ví điện tử"))) {

            String qrUrl = "https://img.vietqr.io/image/MB-0912713606-compact.png"
                    + "?amount=" + tongTienSauGiam
                    + "&addInfo=Thanh%20toan%20tai%20quay"
                    + "&accountName=NGUYEN%20THI%20HA%20LAN";

            model.addAttribute("qrUrl", qrUrl);
        }

        // 8. Gửi thông tin để xác nhận (nếu cần show modal thành công)
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("tongTienGoc", tongTienGoc);
        model.addAttribute("tongTienSauGiam", tongTienSauGiam);
        model.addAttribute("giamGia", giamGia);
        model.addAttribute("tenKhach", khachHang.getTenKh());
        model.addAttribute("hinhThuc", hinhThuc.getTenHinhThuc());
        redirect.addFlashAttribute("hoaDonId", hoaDon.getId());
        redirect.addFlashAttribute("tienKhachDua", tienKhachDua);
        redirect.addFlashAttribute("tienTraLai", tienTraLai);
        // ✅ Sau khi lưu hóa đơn xong thì xóa sạch session để không bị quay lại nhập lại nữa
        session.removeAttribute("gioHang");
        session.removeAttribute("tongTien");
        session.removeAttribute("khachHang");
        session.removeAttribute("giamGia");
        session.removeAttribute("tienKhachDua");


        redirect.addFlashAttribute("success", "🎉 Thanh toán thành công!");
//        return "redirect:/ban-hang/in-hoa-don/" + hoaDon.getId();
        return "redirect:/ban-hang/in-hoa-don/" + hoaDon.getId() + "?redirect=ban-hang/hien-thi";

    }

    // Trang hiển thị hóa đơn
    @GetMapping("/in-hoa-don/{id}")
    public String inHoaDon(@PathVariable("id") Integer id, Model model,
                           @RequestParam(value = "redirect", required = false) String redirect) {
        HoaDon hoaDon = hoaDonRepo.findById(id).orElse(null);
        if (hoaDon == null) {
            return "redirect:/error";
        }

        List<HoaDonChiTiet> chiTietList = hoaDonCTRepo.findByHoaDon_Id(id);

        BigDecimal tongTienHang = chiTietList.stream()
                .map(HoaDonChiTiet::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal giamGia = (hoaDon.getGiamGia() != null) ? BigDecimal.valueOf(hoaDon.getGiamGia().getGiaTriGiam()) : BigDecimal.ZERO;

        BigDecimal tongThanhToan = tongTienHang.subtract(giamGia);

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("chiTietList", chiTietList);
        model.addAttribute("tongTienHang", tongTienHang);
        model.addAttribute("giamGia", giamGia);
        model.addAttribute("tongThanhToan", tongThanhToan);
        model.addAttribute("redirectBack", redirect);

        model.addAttribute("tienKhachDua", model.asMap().getOrDefault("tienKhachDua", BigDecimal.ZERO));
        model.addAttribute("tienTraLai", model.asMap().getOrDefault("tienTraLai", BigDecimal.ZERO));

        return "ViewBanHang/in-hoa-don"; // HTML file
    }

    // Tải xuống PDF
    @GetMapping("/pdf/{id}")
    public void exportPdf(@PathVariable("id") Integer id,
                          HttpServletResponse response) throws IOException, DocumentException, WriterException {

        HoaDon hoaDon = hoaDonRepo.findById(id).orElse(null);
        List<HoaDonChiTiet> chiTietList = hoaDonCTRepo.findByHoaDonId(id);

        if (hoaDon == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=hoa_don_" + URLEncoder.encode(hoaDon.getMaHd(), "UTF-8") + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        document.add(new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle));
        document.add(new Paragraph("Mã hóa đơn: " + hoaDon.getMaHd(), font));
        document.add(new Paragraph("Ngày tạo: " + hoaDon.getNgayTao(), font));
        document.add(new Paragraph("Khách hàng: " + hoaDon.getKhachHang().getTenKh(), font));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Tên sản phẩm");
        table.addCell("Số lượng");
        table.addCell("Đơn giá");
        table.addCell("Thành tiền");
        table.addCell("Trạng thái");

        BigDecimal tongTien = BigDecimal.ZERO;

        for (HoaDonChiTiet ct : chiTietList) {
            table.addCell(ct.getSanPham().getTenSanPham());
            table.addCell(ct.getSoLuong().toString());
            table.addCell(ct.getDonGia().toString());
            table.addCell(ct.getThanhTien().toString());
            table.addCell(ct.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán");
            tongTien = tongTien.add(ct.getThanhTien());
        }

        document.add(table);
        document.add(new Paragraph("Tổng tiền: " + tongTien.toPlainString() + " VND", font));

        if ("Chuyển khoản".equalsIgnoreCase(hoaDon.getHinhThucThanhToan().getTenHinhThuc()) ||
                "Ví điện tử".equalsIgnoreCase(hoaDon.getHinhThucThanhToan().getTenHinhThuc())) {

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Vui lòng chuyển khoản đến:"));
            document.add(new Paragraph("Nguyễn Thị Hà Lan - MB Bank - 0912713606"));
            document.add(new Paragraph("Số tiền: " + tongTien.toPlainString() + " VND"));

            String noiDung = "2|99|0912713606|Nguyen Thi Ha Lan|" + tongTien.toPlainString() + "|Thanh toan hoa don " + hoaDon.getMaHd();
            BufferedImage qrImage = generateQRImage(noiDung, 200, 200);

            ByteArrayOutputStream baosImg = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baosImg);
            Image qr = Image.getInstance(baosImg.toByteArray());
            qr.scaleToFit(150, 150);
            document.add(qr);
        }

        document.close();
    }

    public BufferedImage generateQRImage(String text, int width, int height) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    @GetMapping("/qr-thanh-toan")
    public String hienThiQR(@RequestParam("idHoaDon") Integer idHoaDon, Model model) {
        HoaDon hoaDon = hoaDonRepo.findById(idHoaDon).orElse(null);
        if (hoaDon == null) {
            return "redirect:/ban-hang/hien-thi";
        }

        long tongTien = hoaDon.getTongTien();
        String qrUrl = "https://img.vietqr.io/image/MB-0912713606-compact.png?amount=" + tongTien
                + "&addInfo=Thanh%20toan%20tai%20quay&accountName=NGUYEN%20THI%20HA%20LAN";

        model.addAttribute("qrUrl", qrUrl);

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("hoaDon", hoaDon);
        return "ViewBanHang/qr-thanh-toan";
    }


    @PostMapping("/dat-hang")
    public String xuLyDatHang(@RequestParam Map<String, String> params,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirect) {

        // 1. Kiểm tra đăng nhập
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("adminDangNhap");
        if (taiKhoan == null || taiKhoan.getLoaiTaiKhoan() != 1) {
            return "redirect:/quan-ao/login";
        }

        NhanVien nhanVien = taiKhoan.getNhanVien();

        // ==== 2. ĐỊA CHỈ GIAO HÀNG ====
        String diaChiGiaoHang = params.get("diaChiGiaoHang");
        KhachHang khachHang = null;
        String khachHangIdStr = params.get("khachHangId");

        if (khachHangIdStr != null && !khachHangIdStr.isEmpty()) {
            // Khách đã chọn từ DB → dùng trực tiếp, không validate
            Integer khachHangId = Integer.parseInt(khachHangIdStr);
            khachHang = khachHangRepo.findById(khachHangId).orElse(null);
            if (khachHang == null) {
                redirect.addFlashAttribute("error", "Khách hàng không tồn tại!");
                return "redirect:/ban-hang/hien-thi?tab=dat-hang";
            }

            // Nếu người dùng không nhập địa chỉ mới, giữ nguyên địa chỉ cũ
            if (diaChiGiaoHang == null || diaChiGiaoHang.isEmpty()) {
                diaChiGiaoHang = khachHang.getDiaChi();
            } else if (!diaChiGiaoHang.equals(khachHang.getDiaChi())) {
                // Cập nhật địa chỉ mới cho khách cũ
                khachHang.setDiaChi(diaChiGiaoHang);
                khachHangRepo.save(khachHang);
            }
        }
        else {
            // Khách hàng mới → validate dữ liệu trước khi tạo
            String tenKh = params.get("tenKh");
            String diaChi = params.get("diaChi");
            String sdt = params.get("sdt");

            if(tenKh == null || tenKh.trim().isEmpty() || sdt == null || sdt.trim().isEmpty()) {
                redirect.addFlashAttribute("error", "Vui lòng nhập đầy đủ tên và SĐT khách hàng!");
                return "redirect:/ban-hang/hien-thi?tab=dat-hang";
            }

            if(!sdt.matches("^0\\d{9}$")) { // kiểm tra số điện thoại đúng định dạng
                redirect.addFlashAttribute("error", "SĐT không hợp lệ!");
                return "redirect:/ban-hang/hien-thi?tab=dat-hang";
            }

            // Nếu hợp lệ mới tạo và lưu
            khachHang = new KhachHang();
            khachHang.setTenKh(tenKh);
            khachHang.setDiaChi(diaChiGiaoHang != null ? diaChiGiaoHang : "Việt Nam");
            khachHang.setSdt(sdt);
            khachHang.setTrangThai(1);
            khachHang.setGioiTinh(true);
            khachHang.setNgaySinh(LocalDate.of(2000,1,1));
            khachHang.setQuocTich("Việt Nam");
            khachHang.setEmail("noemail" + System.currentTimeMillis() + "@example.com");
            khachHang.setMaKh("KH" + System.currentTimeMillis());
            khachHang = khachHangRepo.save(khachHang);

        }




        // 3. Lấy hình thức thanh toán
        int idHinhThuc = Integer.parseInt(params.get("hinhThuc"));
        int tongTienGoc = Integer.parseInt(params.get("tongTien"));
        int tongTienSauGiam = tongTienGoc;

        HinhThucThanhToan hinhThuc = thanhToanRepo.findById(idHinhThuc).orElse(null);

        // 4. Xử lý giảm giá
        GiamGia giamGia = null;
        String giamGiaStr = params.get("giamGiaId");
        if (giamGiaStr != null && !giamGiaStr.isEmpty()) {
            int idGiamGia = Integer.parseInt(giamGiaStr);
            giamGia = giamGiaRepo.findById(idGiamGia).orElse(null);

            if (giamGia != null && giamGia.getSoLuong() > 0
                    && LocalDateTime.now().isAfter(giamGia.getNgayBatDau())
                    && LocalDateTime.now().isBefore(giamGia.getNgayKetThuc())) {

                Integer loai = giamGia.getLoaiGiamGia();
                if (loai != null && loai == 1) {
                    tongTienSauGiam = tongTienGoc - (tongTienGoc * giamGia.getGiaTriGiam()) / 100;
                } else {
                    tongTienSauGiam = tongTienGoc - giamGia.getGiaTriGiam();
                }
                if (tongTienSauGiam < 0) tongTienSauGiam = 0;

                giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                giamGiaRepo.save(giamGia);
            }
        }

        // 5. Kiểm tra tồn kho & tạo giỏ hàng
        List<String> loiSanPham = new ArrayList<>();
        Map<Integer, Integer> gioHang = new HashMap<>();

        for (String key : params.keySet()) {
            if (key.startsWith("sp_")) {
                String rawValue = params.get(key);
                if (rawValue == null || rawValue.isEmpty()) continue;

                Integer idSp = Integer.parseInt(key.substring(3));
                int soLuong = Integer.parseInt(rawValue);

                SanPham sp = sanPhamRepo.findById(idSp).orElse(null);
                if (sp != null && soLuong > 0) {
                    if (soLuong > sp.getSoLuong()) {
                        loiSanPham.add("❌ Sản phẩm '" + sp.getTenSanPham() + "' chỉ còn " + sp.getSoLuong() + " cái.");
                    } else {
                        gioHang.put(idSp, soLuong);
                    }
                }
            }
        }

        if (!loiSanPham.isEmpty()) {
            redirect.addFlashAttribute("error", "Không thể đặt hàng:\n" + String.join("\n", loiSanPham));
            return "redirect:/ban-hang/hien-thi?tab=dat-hang";
        }

        // 6. Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHd("DH" + System.currentTimeMillis());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTrangThai(1);
        hoaDon.setTongTien(tongTienSauGiam);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setHinhThucThanhToan(hinhThuc);
        hoaDon.setGiamGia(giamGia);
        hoaDon.setTrangThaiDonHang(TrangThaiDonHang.DA_XAC_NHAN);

        // **Set địa chỉ giao hàng**
        hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);

        hoaDonRepo.save(hoaDon);

        // 7. Lưu chi tiết hóa đơn + trừ tồn kho
        for (Map.Entry<Integer, Integer> entry : gioHang.entrySet()) {
            Integer idSp = entry.getKey();
            Integer soLuong = entry.getValue();

            SanPham sp = sanPhamRepo.findById(idSp).orElse(null);
            if (sp != null) {
                HoaDonChiTiet ct = new HoaDonChiTiet();
                ct.setHoaDon(hoaDon);
                ct.setSanPham(sp);
                ct.setSoLuong(soLuong);
                BigDecimal donGia = sp.getGia();
                BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
                ct.setDonGia(donGia);
                ct.setThanhTien(thanhTien);
                ct.setMaHdct("CT" + System.nanoTime());
                ct.setTrangThai(1);
                hoaDonCTRepo.save(ct);

                sp.setSoLuong(sp.getSoLuong() - soLuong);
                sanPhamRepo.save(sp);
            }
        }

        // 8. QR nếu là chuyển khoản hoặc ví
        if (hinhThuc != null && (
                hinhThuc.getTenHinhThuc().equalsIgnoreCase("Chuyển khoản") ||
                        hinhThuc.getTenHinhThuc().equalsIgnoreCase("Ví điện tử"))) {

            String qrUrl = "https://img.vietqr.io/image/MB-0912713606-compact.png"
                    + "?amount=" + tongTienSauGiam
                    + "&addInfo=Dat%20hang%20online"
                    + "&accountName=NGUYEN%20THI%20HA%20LAN";

            model.addAttribute("qrUrl", qrUrl);
        }

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("tongTienGoc", tongTienGoc);
        model.addAttribute("tongTienSauGiam", tongTienSauGiam);
        model.addAttribute("giamGia", giamGia);
        model.addAttribute("tenKhach", khachHang.getTenKh());
        model.addAttribute("hinhThuc", hinhThuc.getTenHinhThuc());

        redirect.addFlashAttribute("hoaDonId", hoaDon.getId());

        session.removeAttribute("gioHang");
        session.removeAttribute("tongTien");
        session.removeAttribute("khachHang");
        session.removeAttribute("giamGia");

        redirect.addFlashAttribute("success", "🎉 Đặt hàng thành công!");
        return "redirect:/ban-hang/hien-thi?tab=dat-hang";
    }




}

