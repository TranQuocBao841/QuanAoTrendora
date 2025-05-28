package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/tai-khoan")

public class QLTaiKhoanController {

        @Autowired
        TaiKhoanRepo taiKhoanRepo;

        @GetMapping("/hien-thi")
        public String hienThiTaiKhoan(Model model) {
            model.addAttribute("list", taiKhoanRepo.findAll());
            return "/ViewQLTaiKhoan/hien-thi";
        }

        @GetMapping("/update")
        public String showUpdate(@RequestParam("id") Integer id, Model model) {
            model.addAttribute("tk", taiKhoanRepo.findById(id).get());
            return "/ViewQLTaiKhoan/update";
        }

        @PostMapping("/update")
        public String updateTaiKhoan(@ModelAttribute TaiKhoan taiKhoan) {
            taiKhoanRepo.save(taiKhoan);
            return "redirect:/tai-khoan/hien-thi";
        }
}
