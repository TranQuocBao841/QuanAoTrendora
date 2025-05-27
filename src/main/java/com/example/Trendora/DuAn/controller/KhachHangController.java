package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.repository.KhachHangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/khach-hang")
@Controller
public class KhachHangController {
    @Autowired
    KhachHangRepo khachHangRepo;

    @GetMapping("/hien-thi")
    public String showHienThi(Model model){
        model.addAttribute("list",khachHangRepo.findAll());
        return "/ViewKhachHang/hien-thi";
    }

    @PostMapping("/add")
    public String add(KhachHang khachHang){
        khachHangRepo.save(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }

    @GetMapping("/deltal")
    public String showDeltal(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("kh",khachHangRepo.findById(id).get());
        return "/ViewKhachHang/deltal";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("kh",khachHangRepo.findById(id).get());
        return "/ViewKhachHang/update";
    }

    @PostMapping("/update")
    public String update(KhachHang khachHang){
        khachHangRepo.save(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        khachHangRepo.deleteById(id);
        return "redirect:/khach-hang/hien-thi";
    }

}
