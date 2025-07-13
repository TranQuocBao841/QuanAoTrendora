package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.HoaDon;
import com.example.Trendora.DuAn.model.HoaDonChiTiet;
import com.example.Trendora.DuAn.model.KhachHang;
import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import com.example.Trendora.DuAn.repository.KhachHangRepo;
import com.example.Trendora.DuAn.repository.TaiKhoanRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quan-ao")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanRepo taiKhoanRepository;

    @Autowired
    private KhachHangRepo khachHangRepository;

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    @GetMapping("/dangky")
    public String showRegisterForm() {
        return "/ViewTrendora/dangky";
    }

    @PostMapping("/dangky")
    public String register(
            @RequestParam String tenDangNhap,
            @RequestParam String matKhau,
            @RequestParam String email,
            @RequestParam String ngaySinh,
            @RequestParam String diaChi,
            @RequestParam boolean gioiTinh,
            @RequestParam String quocTich,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra tài khoản
        if (taiKhoanRepository.existsByTenDangNhap(tenDangNhap)) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "redirect:/quan-ao/dangky";
        }

        if (taiKhoanRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng!");
            return "redirect:/quan-ao/dangky";
        }

        // Tạo khách hàng
        KhachHang khachHang = new KhachHang();
        khachHang.setTenKh(tenDangNhap);
        khachHang.setEmail(email);
        khachHang.setNgaySinh(LocalDate.parse(ngaySinh));
        khachHang.setDiaChi(diaChi);
        khachHang.setGioiTinh(gioiTinh);
        khachHang.setQuocTich(quocTich);
        khachHang.setMaKh("KH00" + (khachHangRepository.count() + 1));
        khachHang.setTrangThai(1);

        khachHangRepository.save(khachHang);

        // Tạo tài khoản
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(tenDangNhap);
        taiKhoan.setMatKhau(matKhau);
        taiKhoan.setEmail(email);
        taiKhoan.setTrangThai(true);
        taiKhoan.setLoaiTaiKhoan(2);
        taiKhoan.setKhachHang(khachHang);

        taiKhoanRepository.save(taiKhoan);

        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/quan-ao/login";
    }


    @GetMapping("/login")
    public String HienThiDangNhap() {
        return "ViewTrendora/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String matKhau,
                        Model model,
                        HttpSession session) { // 👈 THÊM HttpSession

        TaiKhoan user = taiKhoanRepository.findByEmailAndMatKhau(email, matKhau);

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "ViewTrendora/login";
        }

        if (!user.getTrangThai()) {
            model.addAttribute("error", "Tài khoản của bạn đã bị khóa hoặc ngừng hoạt động!");
            return "ViewTrendora/login";
        }

        // ✅ Lưu thông tin đăng nhập vào session
        session.setAttribute("khachHangDangNhap", user); // 👈 dùng key này để kiểm tra ở giỏ hàng

        if (user.getLoaiTaiKhoan() != null && user.getLoaiTaiKhoan() == 1) {
            session.setAttribute("adminDangNhap", user);
            return "redirect:/admin/san-pham/hien-thi";
        } else {
            return "redirect:/san-pham/hien-thi";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa hết dữ liệu phiên
        return "redirect:/quan-ao/login";
    }


    @GetMapping("/thong-tin")
    public String thongTinTaiKhoan(HttpSession session, Model model) {
        Object user = session.getAttribute("khachHangDangNhap");

        if (user == null) {
            return "redirect:/quan-ao/login"; // Chưa đăng nhập
        }

        TaiKhoan taiKhoan = (TaiKhoan) user;

        // Kiểm tra xem tài khoản có liên kết với khách hàng không
        if (taiKhoan.getKhachHang() == null) {
            model.addAttribute("error", "Tài khoản chưa được liên kết với thông tin khách hàng.");
            return "redirect:/quan-ao/login";
        }

        // Lấy danh sách hóa đơn
        Integer idKhachHang = taiKhoan.getKhachHang().getIdKh();
        List<HoaDon> hoaDonList = hoaDonRepo.findByKhachHang_idKh(idKhachHang);

        model.addAttribute("hoaDonList", hoaDonList);
        model.addAttribute("taiKhoan", taiKhoan);

        Map<Integer, List<HoaDonChiTiet>> chiTietMap = new HashMap<>();
        for (HoaDon hd : hoaDonList) {
            List<HoaDonChiTiet> chiTiets = hoaDonChiTietRepo.findByHoaDon_Id(hd.getId());
            chiTietMap.put(hd.getId(), chiTiets);
        }

        model.addAttribute("chiTietMap", chiTietMap);

        return "ViewTaiKhoanUser/thong-tin";
    }



    @PostMapping("/cap-nhat-thong-tin")
    public String capNhatThongTin(@RequestParam("sdt") String sdt,
                                  @RequestParam("diaChi") String diaChi,
                                  @RequestParam("tenDangNhap") String tenDangNhap,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("khachHangDangNhap");

        if (taiKhoan != null && taiKhoan.getKhachHang() != null) {
            taiKhoan.getKhachHang().setSdt(sdt);
            taiKhoan.getKhachHang().setDiaChi(diaChi);
            taiKhoan.setTenDangNhap(tenDangNhap);
            khachHangRepository.save(taiKhoan.getKhachHang()); // Lưu lại thông tin khách hàng
            taiKhoanRepository.save(taiKhoan);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy tài khoản.");
        }

        return "redirect:/quan-ao/thong-tin";
    }

    @PostMapping("/doi-mat-khau")
    public String doiMatKhau(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("confirmPassword") String confirmPassword,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("khachHangDangNhap");

        if (taiKhoan == null) {
            redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng nhập.");
            return "redirect:/quan-ao/login";
        }

        // Kiểm tra mật khẩu cũ
        if (!taiKhoan.getMatKhau().equals(oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng.");
            return "redirect:/quan-ao/thong-tin";
        }

        // Kiểm tra khớp mật khẩu mới
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không khớp.");
            return "redirect:/quan-ao/thong-tin";
        }

        // Cập nhật mật khẩu
        taiKhoan.setMatKhau(newPassword);
        taiKhoanRepository.save(taiKhoan);

        redirectAttributes.addFlashAttribute("message", "Đổi mật khẩu thành công!");
        return "redirect:/quan-ao/thong-tin";
    }

}
