package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.KichThuoc;
import com.example.Trendora.DuAn.model.KieuDang;
import com.example.Trendora.DuAn.repository.KichThuocRepo;
import com.example.Trendora.DuAn.repository.KieuDangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/kieu-dang")
@Controller
public class KieuDangController {
   @Autowired
   KieuDangRepo kieuDangRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",kieuDangRepo.findAll());
        return "/ViewKieuDang/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewKieuDang/add";
    }

    @PostMapping("/add")
    public String add(KieuDang kieuDang){
        kieuDangRepo.save(kieuDang);
        return "redirect:/kieu-dang/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",kieuDangRepo.findById(id).get());
        return "/ViewKieuDang/update";
    }

    @PostMapping("/update")
    public String Update(KieuDang kieuDang){
        kieuDangRepo.save(kieuDang);
        return "redirect:/kieu-dang/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        kieuDangRepo.deleteById(id);
        return "redirect:/kieu-dang/hien-thi";
    }
}
