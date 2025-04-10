package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.model.TaiKhoan;
import com.example.SmartPhoneHup.DuAn.repository.KhachHangRepo;
import com.example.SmartPhoneHup.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dien-thoai")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanRepo taiKhoanRepository;

    @Autowired
    private KhachHangRepo khachHangRepository;

    @GetMapping("/dangky")
    public String showRegisterForm() {
        return "/ViewSmartPhone/dangky";
    }

    @PostMapping("/dangky")
    public String register(
            @RequestParam String tenDangNhap,
            @RequestParam String matKhau,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra tài khoản đã tồn tại chưa
        if (taiKhoanRepository.existsByTenDangNhap(tenDangNhap)) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "redirect:/dangky";
        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(tenDangNhap);
        taiKhoan.setMatKhau(matKhau);
        taiKhoan.setEmail(email);
        taiKhoan.setLoaiTaiKhoan(2); // Mặc định là khách hàng

        // Kiểm tra xem khách hàng đã tồn tại chưa
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang == null) {
            khachHang = new KhachHang();
            khachHang.setTen(tenDangNhap);

            // Tự động tạo mã khách hàng
            String maKhachHangMoi = "KH00" + (khachHangRepository.count() + 1);
            khachHang.setMaKh(maKhachHangMoi);

            khachHang.setEmail(email != null && !email.isBlank() ? email : "");
            khachHang.setTrangThai(1);

            khachHangRepository.save(khachHang);
        }
        taiKhoan.setKhachHang(khachHang);

        // Lưu tài khoản vào cơ sở dữ liệu
        taiKhoanRepository.save(taiKhoan);

        // Chuyển hướng về trang đăng nhập với thông báo thành công
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/dien-thoai/login";
    }

    @GetMapping("/login")
    public String HienThiDangNhap() {
        return "ViewSmartPhone/login";
    }

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
        if ("1".equalsIgnoreCase(String.valueOf(user.getLoaiTaiKhoan()))){
            return "redirect:/admin/san-pham/hien-thi"; // Trang quản lý dành cho nhân viên
        } else {
            return "redirect:/san-pham/hien-thi"; // Trang bình thường dành cho khách hàng
        }
    }
}
