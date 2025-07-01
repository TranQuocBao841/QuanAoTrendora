package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.KhachHang;
import com.example.Trendora.DuAn.model.TaiKhoan;
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

@Controller
@RequestMapping("/quan-ao")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanRepo taiKhoanRepository;

    @Autowired
    private KhachHangRepo khachHangRepository;

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

        if ("1".equalsIgnoreCase(String.valueOf(user.getLoaiTaiKhoan()))) {
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

}
