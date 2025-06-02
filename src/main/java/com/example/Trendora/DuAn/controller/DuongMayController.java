package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.CoAo;
import com.example.Trendora.DuAn.model.DuongMay;
import com.example.Trendora.DuAn.repository.CoAoRepo;
import com.example.Trendora.DuAn.repository.DuongMayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/duong-may")
@Controller
public class DuongMayController {
   @Autowired
   DuongMayRepo duongMayRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",duongMayRepo.findAll());
        return "/ViewDuongMay/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewDuongMay/add";
    }

    @PostMapping("/add")
    public String add(DuongMay duongMay){
        duongMayRepo.save(duongMay);
        return "redirect:/duong-may/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",duongMayRepo.findById(id).get());
        return "/ViewDuongMay/update";
    }

    @PostMapping("/update")
    public String Update(DuongMay duongMay){
        duongMayRepo.save(duongMay);
        return "redirect:/duong-may/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        duongMayRepo.deleteById(id);
        return "redirect:/duong-may/hien-thi";
    }
}
