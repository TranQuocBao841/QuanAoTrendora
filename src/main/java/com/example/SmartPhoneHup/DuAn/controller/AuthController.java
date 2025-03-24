package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.model.NhanVien;
import com.example.SmartPhoneHup.DuAn.model.TaiKhoan;
import com.example.SmartPhoneHup.DuAn.repository.KhachHangRepo;
import com.example.SmartPhoneHup.DuAn.repository.NhanVienRepo;
import com.example.SmartPhoneHup.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/dien-thoai")
public class AuthController {
    @Autowired
    private TaiKhoanRepo taiKhoanRepository;


    public AuthController(TaiKhoanRepo taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    // Hiển thị form đăng ký
    @GetMapping("/dangky")
    public String showForm(Model model) {
        model.addAttribute("request", new TaiKhoan());
        return "/ViewSmartPhone/dangky"; // Trả về trang đăng ký
    }

    // Xử lý đăng ký
    @PostMapping("/dangky")
    public String register(@ModelAttribute TaiKhoan taiKhoan, Model model) {
        Optional<TaiKhoan> taikhoan = taiKhoanRepository.findByTenDangNhap(taiKhoan.getTenDangNhap());
        if (taikhoan.isPresent()) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "/ViewSmartPhone/dangky"; // Trả về trang đăng ký
        }
        taiKhoanRepository.save(taiKhoan);
        return "redirect:/dien-thoai/login";
    }












    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String HienThiDangNhap() {
        return "ViewSmartPhone/login";
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String tenDangNhap,
                        @RequestParam String matKhau,
                        Model model) {
        TaiKhoan user = taiKhoanRepository.findByTenDangNhapAndMatKhau(tenDangNhap, matKhau);
        if (user == null) {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            return "ViewSmartPhone/login";
        }
        model.addAttribute("user", user);
        return "home";
    }
}