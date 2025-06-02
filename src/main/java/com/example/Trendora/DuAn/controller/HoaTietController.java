package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.DuongMay;
import com.example.Trendora.DuAn.model.HoaTiet;
import com.example.Trendora.DuAn.repository.DuongMayRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import com.example.Trendora.DuAn.repository.HoaTietRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/hoa-tiet")
@Controller
public class HoaTietController {
   @Autowired
   HoaTietRepo hoaTietRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",hoaTietRepo.findAll());
        return "/ViewHoaTiet/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewHoaTiet/add";
    }

    @PostMapping("/add")
    public String add(HoaTiet hoaTiet){
        hoaTietRepo.save(hoaTiet);
        return "redirect:/hoa-tiet/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",hoaTietRepo.findById(id).get());
        return "/ViewHoaTiet/update";
    }

    @PostMapping("/update")
    public String Update(HoaTiet hoaTiet){
        hoaTietRepo.save(hoaTiet);
        return "redirect:/hoa-tiet/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        hoaTietRepo.deleteById(id);
        return "redirect:/hoa-tiet/hien-thi";
    }
}
