package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.model.SanPham;
import com.example.SmartPhoneHup.DuAn.repository.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/san-pham")
@Controller
public class SanPhamController {
    @Autowired
    SanPhamRepo sanPhamRepo;

    @GetMapping("hien-thi")
    public String Hienthi(Model model){
        model.addAttribute("list",sanPhamRepo.findAll());
        return "/ViewSanpham/hien-thi";
    }
    @PostMapping("/add")
    public String add(SanPham sanPham){
        sanPhamRepo.save(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/deltal")
    public String showDeltal(@RequestParam("id") Integer id , Model model){
        model.addAttribute("sp",sanPhamRepo.findById(id).get());
        return "/ViewSanPham/deltal";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("sp",sanPhamRepo.findById(id).get());
        return "/ViewSanPham/update";
    }

    @PostMapping("/update")
    public String update(SanPham sanPham){
        sanPhamRepo.save(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        sanPhamRepo.deleteById(id);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("/timkiem")
    public String timKiemSanPham(@RequestParam(value = "ten", required = false, defaultValue = "") String ten, Model model) {
        List<SanPham> danhSachSanPham = sanPhamRepo.findByTenContainingIgnoreCase(ten);
        model.addAttribute("list", danhSachSanPham);
        model.addAttribute("ten", ten); // Giữ lại giá trị tìm kiếm trên giao diện
        return "ViewSanPham/hien-thi";
    }
}
