package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.KieuDang;
import com.example.Trendora.DuAn.model.MauSac;
import com.example.Trendora.DuAn.repository.KieuDangRepo;
import com.example.Trendora.DuAn.repository.MauSacRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/mau-sac")
@Controller
public class MauSacController {
   @Autowired
   MauSacRepo mauSacRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",mauSacRepo.findAll());
        return "/ViewMauSac/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewMauSac/add";
    }

    @PostMapping("/add")
    public String add(MauSac mauSac){
        mauSacRepo.save(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",mauSacRepo.findById(id).get());
        return "/ViewMauSac/update";
    }

    @PostMapping("/update")
    public String Update(MauSac mauSac){
        mauSacRepo.save(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        mauSacRepo.deleteById(id);
        return "redirect:/mau-sac/hien-thi";
    }
}
