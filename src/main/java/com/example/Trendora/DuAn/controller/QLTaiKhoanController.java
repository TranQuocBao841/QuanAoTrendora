package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tai-khoan")

public class QLTaiKhoanController {

    @Autowired
    TaiKhoanRepo taiKhoanRepo;

    @GetMapping("/hien-thi")
    public String hienThiTaiKhoan(Model model) {
        model.addAttribute("list", taiKhoanRepo.findAll());
        return "/ViewQLTaiKhoan/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("taiKhoan", taiKhoanRepo.findById(id).get());
        model.addAttribute("list", taiKhoanRepo.findAll());
        return "/ViewQLTaiKhoan/update";
    }

    @PostMapping("/update")
    public String updateTaiKhoan(@ModelAttribute TaiKhoan taiKhoan) {
        taiKhoanRepo.save(taiKhoan);
        return "redirect:/tai-khoan/hien-thi";
    }
    @GetMapping("/timkiem")
    public String timKiemTaiKhoan(@RequestParam(value = "ten", required = false, defaultValue = "") String ten, Model model) {
        List<TaiKhoan> danhSachTaiKhoan = taiKhoanRepo.findSanPhamsByTenDangNhapContains(ten);
        model.addAttribute("list", danhSachTaiKhoan);
        model.addAttribute("ten", ten); // Giữ lại giá trị tìm kiếm trên giao diện
        return "ViewQLTaiKhoan/hien-thi";
    }

}
