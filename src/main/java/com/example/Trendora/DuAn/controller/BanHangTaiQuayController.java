package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    @GetMapping("/hien-thi")
    public String hienThiBanHang(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "danhMuc", required = false) Integer danhMucId,
                                 @RequestParam(value = "mauSac", required = false) Integer mauSac,
                                 @RequestParam(value = "kichThuoc", required = false) Integer kichThuoc,
                                 Model model) {

        List<SanPham> danhSachSanPham;
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
        if (taiKhoan == null || taiKhoan.getNhanVien() == null) {
            redirect.addFlashAttribute("error", "Vui lòng đăng nhập để thanh toán.");
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

                if (giamGia.getLoaiGiamGia().equalsIgnoreCase("PERCENT")) {
                    tongTienSauGiam = tongTienGoc - (tongTienGoc * giamGia.getGiaTriGiam()) / 100;
                } else {
                    tongTienSauGiam = tongTienGoc - giamGia.getGiaTriGiam();
                }

                if (tongTienSauGiam < 0) tongTienSauGiam = 0;

                // Trừ số lượng phiếu còn lại
                giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                giamGiaRepo.save(giamGia);
            }
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

        redirect.addFlashAttribute("success", "🎉 Thanh toán thành công!");
        return "redirect:/ban-hang/hien-thi";
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
}
