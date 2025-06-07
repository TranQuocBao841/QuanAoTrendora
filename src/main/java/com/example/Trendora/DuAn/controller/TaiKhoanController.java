package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.KhachHang;
import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.KhachHangRepo;
import com.example.Trendora.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            RedirectAttributes redirectAttributes) {

        // Kiểm tra tài khoản đã tồn tại chưa
        if (taiKhoanRepository.existsByTenDangNhap(tenDangNhap)) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "redirect:/quan-ao/dangky";
        }

        //kiem tra email tồn tai
        if (taiKhoanRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng để đăng ký tài khoản khác!");
            return "redirect:/quan-ao/dangky";
        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(tenDangNhap);
        taiKhoan.setMatKhau(matKhau);
        taiKhoan.setEmail(email);
        taiKhoan.setLoaiTaiKhoan(2); // Mặc định là khách hàng
        taiKhoan.setTrangThai(true); //mặc định hoạt động

        // Kiểm tra xem khách hàng đã tồn tại chưa
        KhachHang khachHang = khachHangRepository.findByEmail(email);
        if (khachHang == null) {
            khachHang = new KhachHang();
            khachHang.setTenKh(tenDangNhap);

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
        return "redirect:/quan-ao/login";
    }

    @GetMapping("/login")
    public String HienThiDangNhap() {
        return "ViewTrendora/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String matKhau,
                        Model model) {

        TaiKhoan user = taiKhoanRepository.findByEmailAndMatKhau(email, matKhau);

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "ViewTrendora/login";
        }

        // Kiểm tra trạng thái tài khoản
        if (!user.getTrangThai()) {
            model.addAttribute("error", "Tài khoản của bạn đã bị khóa hoặc ngừng hoạt động!");
            return "ViewTrendora/login";
        }

        model.addAttribute("user", user);

        if ("1".equalsIgnoreCase(String.valueOf(user.getLoaiTaiKhoan()))) {
            return "redirect:/admin/san-pham/hien-thi"; // Trang quản lý dành cho nhân viên
        } else {
            return "redirect:/san-pham/hien-thi"; // Trang bình thường dành cho khách hàng
        }
    }

}
