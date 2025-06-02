package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.HoaTiet;
import com.example.Trendora.DuAn.model.KhuyAo;
import com.example.Trendora.DuAn.repository.HoaTietRepo;
import com.example.Trendora.DuAn.repository.KhuyAoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/khuy-ao")
@Controller
public class KhuyAoController {
   @Autowired
   KhuyAoRepo khuyAoRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",khuyAoRepo.findAll());
        return "/ViewKhuyAo/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewKhuyAo/add";
    }

    @PostMapping("/add")
    public String add(KhuyAo khuyAo){
        khuyAoRepo.save(khuyAo);
        return "redirect:/khuy-ao/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",khuyAoRepo.findById(id).get());
        return "/ViewKhuyAo/update";
    }

    @PostMapping("/update")
    public String Update(KhuyAo khuyAo){
        khuyAoRepo.save(khuyAo);
        return "redirect:/khuy-ao/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        khuyAoRepo.deleteById(id);
        return "redirect:/khuy-ao/hien-thi";
    }
}
