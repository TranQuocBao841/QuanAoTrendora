package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.*;
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
import java.util.Optional;

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
                        HttpSession session) {

        TaiKhoan user = taiKhoanRepository.findByEmailAndMatKhau(email, matKhau);

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "ViewTrendora/login";
        }

        if (!user.getTrangThai()) {
            model.addAttribute("error", "Tài khoản của bạn đã bị khóa hoặc ngừng hoạt động!");
            return "ViewTrendora/login";
        }

        // ✅ Xoá session cũ trước khi set
//        session.removeAttribute("khachHangDangNhap");
//        session.removeAttribute("adminDangNhap");

        // ✅ Nếu là Admin
        if (user.getLoaiTaiKhoan() != null && user.getLoaiTaiKhoan() == 1) {
            session.setAttribute("adminDangNhap", user);
            return "redirect:/admin/san-pham/hien-thi";
        }
        // ✅ Nếu là User
        else {
            session.setAttribute("khachHangDangNhap", user);
            return "redirect:/san-pham/trang-chu";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("khachHangDangNhap");
        return "redirect:/quan-ao/login";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(HttpSession session) {
        session.removeAttribute("adminDangNhap");
        return "redirect:/quan-ao/login";
    }



    @GetMapping("/thong-tin")
    public String thongTinTaiKhoan(HttpSession session, Model model) {
        Object user = session.getAttribute("khachHangDangNhap");

        if (user == null) {
            return "redirect:/quan-ao/login"; // Chưa đăng nhập
        }

        TaiKhoan taiKhoan = (TaiKhoan) user;

        if (taiKhoan.getKhachHang() == null) {
            model.addAttribute("error", "Tài khoản chưa được liên kết với thông tin khách hàng.");
            return "redirect:/quan-ao/login";
        }

        // Lấy danh sách hóa đơn
        Integer idKhachHang = taiKhoan.getKhachHang().getIdKh();
        List<HoaDon> hoaDonList = hoaDonRepo.findByKhachHang_idKh(idKhachHang);
        List<HoaDon> hoaDonHoanThanhList = hoaDonList.stream()
                .filter(hd -> hd.getTrangThaiDonHang() == TrangThaiDonHang.DA_HOAN_THANH)
                .toList();

        model.addAttribute("hoaDonList", hoaDonList);
        model.addAttribute("hoaDonDaHoanThanh", hoaDonHoanThanhList);
        model.addAttribute("taiKhoan", taiKhoan);

        Map<Integer, List<HoaDonChiTiet>> chiTietMap = new HashMap<>();
        for (HoaDon hd : hoaDonList) {
            List<HoaDonChiTiet> chiTiets = hoaDonChiTietRepo.findByHoaDon_Id(hd.getId());
            chiTietMap.put(hd.getId(), chiTiets);
        }

        model.addAttribute("chiTietMap", chiTietMap);

        model.addAttribute("steps", TrangThaiDonHang.values());

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
    @Autowired
    SanPhamRepo spr;
    @Autowired GiamGiaRepo ggr;
    @PostMapping("/hoa-don/huy/{id}")
    public String huyHoaDon(@PathVariable("id") Integer id,
                            @RequestParam("lyDoHuy") String lyDoHuy,
                            RedirectAttributes redirect) {
        Optional<HoaDon> optional = hoaDonRepo.findById(id);
        if (optional.isPresent()) {
            HoaDon hd = optional.get();

            // ✅ Chỉ hủy nếu trạng thái đơn hàng đang "CHỜ XÁC NHẬN" hoặc "CHƯA THANH TOÁN"
            if (hd.getTrangThai() == 0 || hd.getTrangThaiDonHang() == TrangThaiDonHang.CHO_XAC_NHAN) {

                // --- 1. Hoàn lại số lượng sản phẩm ---
                List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepo.findByHoaDonId(hd.getId());
                for (HoaDonChiTiet ctd : chiTietList) {
                    SanPham sp = ctd.getSanPham();
                    if (sp != null) {
                        sp.setSoLuong(sp.getSoLuong() + ctd.getSoLuong());
                        spr.save(sp); // ✅ nhớ save lại
                    }
                }

                // --- 2. Hoàn lại phiếu giảm giá nếu có ---
                if (hd.getGiamGia() != null) {
                    GiamGia phieu = hd.getGiamGia();
                    phieu.setSoLuong(phieu.getSoLuong() + 1); // hoàn lại 1 lượt
                    ggr.save(phieu);
                }

                // --- 3. Cập nhật trạng thái hóa đơn ---
                hd.setTrangThai(2); // 2 = Đã hủy
                hd.setTrangThaiDonHang(TrangThaiDonHang.DA_HUY); // nếu bạn có enum cho trạng thái đơn hàng
                hd.setLyDoHuy(lyDoHuy); // lưu lý do hủy
                hoaDonRepo.save(hd);

                redirect.addFlashAttribute("success", "✅ Đơn hàng đã được hủy.");
            } else {
                redirect.addFlashAttribute("error", "❌ Không thể hủy đơn hàng đã thanh toán hoặc đã xử lý.");
            }
        } else {
            redirect.addFlashAttribute("error", "❌ Không tìm thấy hóa đơn.");
        }
        return "redirect:/quan-ao/thong-tin"; // Trang danh sách đơn hàng khách hàng
    }



}
