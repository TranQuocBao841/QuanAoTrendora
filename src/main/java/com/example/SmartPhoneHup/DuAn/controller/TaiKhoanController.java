package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.model.NhanVien;
import com.example.SmartPhoneHup.DuAn.model.TaiKhoan;
import com.example.SmartPhoneHup.DuAn.repository.KhachHangRepo;
import com.example.SmartPhoneHup.DuAn.repository.NhanVienRepo;
import com.example.SmartPhoneHup.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/dien-thoai")
public class TaiKhoanController {

    @Autowired
    private TaiKhoanRepo taiKhoanRepository;

    @Autowired
    private KhachHangRepo khachHangRepository;

    @Autowired
    private NhanVienRepo nhanVienRepository;

    @GetMapping("/dangky")
    public String showRegisterForm() {
        return "/ViewSmartPhone/dangky";
    }

    @PostMapping("/dangky")
    public String register(
            @RequestParam String tenDangNhap,
            @RequestParam String matKhau,
            @RequestParam Integer loaiTaiKhoan,
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
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        taiKhoan.setEmail(email);
        // Xử lý linh hoạt dựa vào loại tài khoản
        if (loaiTaiKhoan == 2) { // Khách hàng
            KhachHang khachHang = khachHangRepository.findByEmail(tenDangNhap);
            if (khachHang == null) {
                // Tạo mới khách hàng nếu chưa có
                khachHang = new KhachHang();
                khachHang.setTen(tenDangNhap);

                // Tự động tạo ma_kh (ví dụ: KH001, KH002,...)
                String maKhachHangMoi = "KH00" + (khachHangRepository.count() + 1);
                khachHang.setMaKh(maKhachHangMoi);

                // Kiểm tra và xử lý email (nếu có)
                String emails = taiKhoan.getEmail();
                if (emails == null || emails.isBlank()) {
                    khachHang.setEmail(""); // Gán null nếu email trống
                } else {
                    khachHang.setEmail(emails);
                }

                // Đặt trạng thái mặc định (1: Active)
                khachHang.setTrangThai(1);

                khachHangRepository.save(khachHang);
            }
            taiKhoan.setKhachHang(khachHang);



    } else if (loaiTaiKhoan == 1) { // Nhân viên
            NhanVien nhanVien = nhanVienRepository.findByEmail(tenDangNhap);
            if (nhanVien == null) {
                nhanVien = new NhanVien();
                nhanVien.setTen(tenDangNhap);
                // tự động tăng mã nv
                String maNhanVienMoi = "NV00" + (nhanVienRepository.count() + 1);
                nhanVien.setMaNv(maNhanVienMoi);
                // kiểm tra email
                String emails = taiKhoan.getEmail();
                if (emails == null || emails.isBlank()) {
                    nhanVien.setEmail(""); // Gán null nếu email trống
                } else {
                    nhanVien.setEmail(emails);
                }

                // Đặt trạng thái mặc định (1: Active)
                nhanVien.setTrangThai(1);
                nhanVienRepository.save(nhanVien);
            }
            taiKhoan.setNhanVien(nhanVien);
        }

        // Lưu tài khoản vào cơ sở dữ liệu
        taiKhoanRepository.save(taiKhoan);

        // Chuyển hướng về trang đăng nhập với thông báo thành công
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
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