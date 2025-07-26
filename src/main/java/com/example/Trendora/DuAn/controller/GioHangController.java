package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {


    @Autowired
    private SanPhamRepo sanPhamRepo;


    @Autowired
    GiamGiaRepo giamGiaRepo;

    @Autowired
    ObjectMapper objectmapper;
    @Autowired
    private HoaDonRepo hdr;
    @Autowired
    private HoaDonChiTietRepo hdcr;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @GetMapping("/gio-hang")
    public String show (){
        return "gio_hang/view1";
    }


    @GetMapping("/add")
    public String themVaoGio(@RequestParam("id") Integer idSanPham,
                             @RequestParam(defaultValue = "1") int soLuong,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        // 🔒 Kiểm tra đăng nhập
        Object user = session.getAttribute("khachHangDangNhap");
        if (user == null) {
            // ✅ Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập trước khi thêm sản phẩm vào giỏ hàng.");
            return "redirect:/quan-ao/login";

        }

        // ✅ Nếu đã đăng nhập thì tiếp tục thêm vào giỏ hàng
        Optional<SanPham> optional = sanPhamRepo.findById(idSanPham);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/san-pham/hien-thi";
        }

        SanPham sp = optional.get();

        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            cart = new Cart();
        }

        CartItem item = new CartItem(
                sp.getId(),
                sp.getTenSanPham(),
                null,
                null,
                soLuong,
                sp.getGia(),
                sp.getAnhSanPham()
        );

        cart.addItem(item);
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Đã thêm vào giỏ hàng!");
        return "redirect:/gio-hang/hien-thi";
    }


    @GetMapping("/hien-thi")
    public String xemGio(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("gioHang", cart);
        }

        // Lấy mã giảm giá từ session
        GiamGia giamGia = (GiamGia) session.getAttribute("maGiamGiaDaApDung");

        // Tính tổng
        BigDecimal tongTienTruocGiam = cart.getTongTien();
        BigDecimal tongTienSauGiam = cart.getTongTienSauGiam(giamGia);

        // Truyền dữ liệu ra view
        model.addAttribute("cart", cart);
        model.addAttribute("giamGiaDangApDung", giamGia);
        model.addAttribute("tongTienTruocGiam", tongTienTruocGiam);
        model.addAttribute("tongTienSauGiam", tongTienSauGiam);

        List<GiamGia> danhSachGiamGia = giamGiaRepo.findMaGiamGiaConHieuLuc(LocalDateTime.now());
        model.addAttribute("danhSachGiamGia", danhSachGiamGia);

        return "gio_hang/view1";
    }












    @PostMapping("/update-all")
    public String updateAll(@RequestParam("ids") List<Integer> ids,
                            @RequestParam("quantities") List<Integer> quantities,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (cart != null) {
            for (int i = 0; i < ids.size(); i++) {
                Integer id = ids.get(i);
                Integer newQuantity = quantities.get(i);

                CartItem item = cart.getItems().get(id);
                if (item != null) {
                    int soLuongTon = sanPhamRepo.findById(id).get().getSoLuong(); // Lấy tồn kho từ DB

                    if (newQuantity > soLuongTon) {
                        hasError = true;
                        errorMsg.append("Sản phẩm '").append(item.getTenSanPham())
                                .append("' chỉ còn ").append(soLuongTon)
                                .append(" sản phẩm trong kho.");
                    } else {
                        item.setSoLuong(newQuantity);
                    }
                }
            }
            session.setAttribute("gioHang", cart); // Cập nhật lại session
        }

        if (hasError) {
            redirectAttributes.addFlashAttribute("error", errorMsg.toString());
        } else {
            redirectAttributes.addFlashAttribute("success", "Cập nhật giỏ hàng thành công.");
        }

        return "redirect:/gio-hang/hien-thi";
    }


    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer idSanPham,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn hiện không có sản phẩm nào.");
            return "redirect:/gio-hang/hien-thi";
        }

        CartItem item = cart.getItemById(idSanPham);
        if (item == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không có trong giỏ hàng.");
            return "redirect:/gio-hang/hien-thi";
        }

        cart.removeItem(idSanPham);
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được xóa khỏi giỏ hàng!");

        return "redirect:/gio-hang/hien-thi";
    }

    @PostMapping("/xoa-tat-ca")
    public String xoaTatCa(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("gioHang");

        if (cart != null) {
            cart.getItems().clear(); // Xóa toàn bộ sản phẩm
            session.setAttribute("gioHang", cart); // Cập nhật lại session
        }

        redirectAttributes.addFlashAttribute("success", "Đã xóa toàn bộ sản phẩm trong giỏ hàng.");
        return "redirect:/gio-hang/hien-thi";
    }

    //giảm giá
    @PostMapping("/ap-ma-giam")
    public String apDungMaGiamGia(@RequestParam("tenGiamGia") String maGiamGia,
                                  HttpSession session,
                                  RedirectAttributes redirect) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null || cart.getAllItems().isEmpty()) {
            redirect.addFlashAttribute("loiMaGiam", "Giỏ hàng trống. Không thể áp dụng mã giảm giá.");
            return "redirect:/gio-hang/hien-thi";
        }

        Optional<GiamGia> optionalGiamGia = giamGiaRepo.findByMaGiamGia(maGiamGia);
        if (optionalGiamGia.isEmpty()) {
            redirect.addFlashAttribute("loiMaGiam", "Mã giảm giá không tồn tại.");
            return "redirect:/gio-hang/hien-thi";
        }

        GiamGia giamGia = optionalGiamGia.get();
        if (giamGia.getTrangThai() != 1 ||
                giamGia.getNgayBatDau().isAfter(LocalDateTime.now()) ||
                giamGia.getNgayKetThuc().isBefore(LocalDateTime.now())) {

            redirect.addFlashAttribute("loiMaGiam", "Mã giảm giá đã hết hạn hoặc không hợp lệ.");
            return "redirect:/gio-hang/hien-thi";
        }

        session.setAttribute("maGiamGiaDaApDung", giamGia);
        redirect.addFlashAttribute("thongBaoThanhCong", "Áp dụng mã giảm giá thành công!");
        return "redirect:/gio-hang/hien-thi";
    }
    @GetMapping("/thanh-toan")
    public String hienThiThanhToan(
            @RequestParam(value = "tongTien", required = false) BigDecimal tongTien,
            Model model,
            HttpSession session) {

        if (tongTien == null) {
            return "redirect:/gio-hang/hien-thi";
        }

        Cart cart = (Cart) session.getAttribute("gioHang");
        GiamGia giamGia = (GiamGia) session.getAttribute("maGiamGiaDaApDung");

        BigDecimal tongTienTruocGiam = cart != null ? cart.getTongTien() : BigDecimal.ZERO;

        // Thêm vào model
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("tongTienTruocGiam", tongTienTruocGiam);
        model.addAttribute("giamGiaDangApDung", giamGia);
        model.addAttribute("gioHang", cart);

        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("khachHangDangNhap");
        if (taiKhoan != null && taiKhoan.getKhachHang() != null) {
            model.addAttribute("khachHang", taiKhoan.getKhachHang());
        }

        return "ViewThanhToan/viewtt";
    }


    @Autowired
    HinhThucThanhToanRepo htr;
    @PostMapping("/thanh-toan")
    public String xuLyThanhToan(
            @RequestParam("idHinhThuc") Integer idHinhThuc,

            @RequestParam("tongTien") Double tongTien,
            @RequestParam(value = "ghiChu", required = false) String ghiChu,
            @RequestParam("diaChi") String diaChi,

            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("khachHangDangNhap");
        GiamGia giamGia = (GiamGia) session.getAttribute("maGiamGiaDaApDung");

        if (taiKhoan == null || cart == null || cart.getAllItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không thể thanh toán. Vui lòng đăng nhập và thêm sản phẩm.");
            return "redirect:/gio-hang/hien-thi";
        }

        HinhThucThanhToan hinhThuc = htr.findById(idHinhThuc).orElse(null);

        // Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHd("HD" + System.currentTimeMillis());
        hoaDon.setKhachHang(taiKhoan.getKhachHang());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setHinhThucThanhToan(hinhThuc);
        hoaDon.setTongTien(tongTien.intValue());
        hoaDon.setTrangThai(idHinhThuc == 2 ? 1 : 0);
        NhanVien nvMacDinh = nhanVienRepo.findById(1).orElse(null); // ID = 1 là nhân viên mặc định
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setDiaChiGiaoHang(diaChi);
        hoaDon.setTrangThaiDonHang(TrangThaiDonHang.CHO_XAC_NHAN);
        hoaDon.setNhanVien(nvMacDinh);

        if (giamGia != null) {
            hoaDon.setGiamGia(giamGia);

            if (giamGia.getSoLuong() != null && giamGia.getSoLuong() > 0) {
                giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                giamGiaRepo.save(giamGia); // lưu lại
            }
        }

        hdr.save(hoaDon);

        try {
            for (CartItem item : cart.getAllItems()) {
                // Trừ tồn kho
                SanPham sanPham = sanPhamRepo.findById(item.getId()).orElse(null);
                if (sanPham != null) {
                    sanPham.setSoLuong(sanPham.getSoLuong() - item.getSoLuong());
                    sanPhamRepo.save(sanPham);
                }

                // Lưu chi tiết hóa đơn
                HoaDonChiTiet chiTiet = new HoaDonChiTiet();
                chiTiet.setMaHdct("HDCT" + System.currentTimeMillis() + "_" + item.getId());
                chiTiet.setHoaDon(hoaDon);
                chiTiet.setSanPham(sanPham);
                chiTiet.setSoLuong(item.getSoLuong());
                chiTiet.setDonGia(item.getDonGia());
                chiTiet.setThanhTien(item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())));
                chiTiet.setTrangThai(hoaDon.getTrangThai());
                hdcr.save(chiTiet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu hóa đơn chi tiết.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Xóa session giỏ hàng và mã giảm giá
        session.removeAttribute("gioHang");
        session.removeAttribute("maGiamGiaDaApDung");

        redirectAttributes.addFlashAttribute("success", "Thanh toán thành công!");
        return "redirect:/gio-hang/cam-on";
    }


    @GetMapping("/cam-on")
    public String hienThiCamOn() {
        return "ViewThanhToan/camon"; // Gọi đến file camon.html
    }

}

