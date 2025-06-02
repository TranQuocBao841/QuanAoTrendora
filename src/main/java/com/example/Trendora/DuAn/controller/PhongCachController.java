package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.MauSac;
import com.example.Trendora.DuAn.model.PhongCach;
import com.example.Trendora.DuAn.repository.MauSacRepo;
import com.example.Trendora.DuAn.repository.PhongCachRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/phong-cach")
@Controller
public class PhongCachController {
   @Autowired
   PhongCachRepo phongCachRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",phongCachRepo.findAll());
        return "/ViewPhongCach/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewPhongCach/add";
    }

    @PostMapping("/add")
    public String add(PhongCach phongCach){
        phongCachRepo.save(phongCach);
        return "redirect:/phong-cach/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",phongCachRepo.findById(id).get());
        return "/ViewPhongCach/update";
    }

    @PostMapping("/update")
    public String Update(PhongCach phongCach){
        phongCachRepo.save(phongCach);
        return "redirect:/phong-cach/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        phongCachRepo.deleteById(id);
        return "redirect:/phong-cach/hien-thi";
    }
}
