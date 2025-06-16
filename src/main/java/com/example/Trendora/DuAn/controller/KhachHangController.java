package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.KhachHang;
import com.example.Trendora.DuAn.repository.KhachHangRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/khach-hang")
@Controller
public class KhachHangController {
    @Autowired
    KhachHangRepo khachHangRepo;

    @GetMapping("/hien-thi")
    public String showHienThi(@RequestParam(name = "maKh", required = false) String maKh, Model model){
        List<KhachHang> list;
        if (maKh != null && !maKh.isEmpty()) {
            list = khachHangRepo.findByMaKhContainingIgnoreCase(maKh);
        } else {
            list = khachHangRepo.findAll();
        }
        model.addAttribute("list", list);
        return "/ViewKhachHang/hien-thi";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "/ViewKhachHang/add";
    }

    @PostMapping("/add")
    public String add(@Valid KhachHang khachHang, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("khachHang", khachHang);
            return "/ViewKhachHang/add";
        }
        khachHangRepo.save(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }


    @GetMapping("/deltail")
    public String showDeltal(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("kh",khachHangRepo.findById(id).get());
        return "/ViewKhachHang/deltal";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("khachHang",khachHangRepo.findById(id).get());
        return "/ViewKhachHang/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachHang") KhachHang khachHang,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("khachHang", khachHang);
            return "/ViewKhachHang/update";
        }
        khachHangRepo.save(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }



    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        khachHangRepo.deleteById(id);
        return "redirect:/khach-hang/hien-thi";
    }

}
