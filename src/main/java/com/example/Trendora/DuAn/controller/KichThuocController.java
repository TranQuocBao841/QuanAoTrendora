package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.KhuyAo;
import com.example.Trendora.DuAn.model.KichThuoc;
import com.example.Trendora.DuAn.repository.KhuyAoRepo;
import com.example.Trendora.DuAn.repository.KichThuocRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/kich-thuoc")
@Controller
public class KichThuocController {
   @Autowired
   KichThuocRepo kichThuocRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",kichThuocRepo.findAll());
        return "/ViewKichThuoc/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewKichThuoc/add";
    }

    @PostMapping("/add")
    public String add(KichThuoc kichThuoc){
        kichThuocRepo.save(kichThuoc);
        return "redirect:/kich-thuoc/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",kichThuocRepo.findById(id).get());
        return "/ViewKichThuoc/update";
    }

    @PostMapping("/update")
    public String Update(KichThuoc kichThuoc){
        kichThuocRepo.save(kichThuoc);
        return "redirect:/kich-thuoc/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        kichThuocRepo.deleteById(id);
        return "redirect:/kich-thuoc/hien-thi";
    }
}
