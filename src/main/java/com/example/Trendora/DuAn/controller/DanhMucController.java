package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.DanhMuc;
import com.example.Trendora.DuAn.repository.DanhMucRepo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/danh-muc")
@Controller
public class DanhMucController {

    @Autowired
    private DanhMucRepo danhMucRepo;

    // Hiển thị danh sách danh mục
    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        model.addAttribute("list", danhMucRepo.findAll());
        return "/ViewDanhMuc/hien-thi";
    }

    // Hiển thị form thêm
    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("danhMuc", new DanhMuc());
        return "/ViewDanhMuc/add";
    }

    // Xử lý thêm danh mục (có validate)
    @PostMapping("/add")
    public String addDanhMuc(@Valid @ModelAttribute("danhMuc") DanhMuc danhMuc,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "/ViewDanhMuc/add";
        }
        danhMucRepo.save(danhMuc);
        return "redirect:/danh-muc/hien-thi";
    }

    // Hiển thị form cập nhật
    @GetMapping("/update")
    public String showFormUpdate(@RequestParam("id") Integer id, Model model) {
        DanhMuc danhMuc = danhMucRepo.findById(id).orElse(null);
        if (danhMuc == null) {
            return "redirect:/danh-muc/hien-thi";
        }
        model.addAttribute("danhMuc", danhMuc);
        return "/ViewDanhMuc/update";
    }

    // Xử lý cập nhật danh mục (có validate)
    @PostMapping("/update")
    public String updateDanhMuc(@Valid @ModelAttribute("danhMuc") DanhMuc danhMuc,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "/ViewDanhMuc/update";
        }
        danhMucRepo.save(danhMuc);
        return "redirect:/danh-muc/hien-thi";
    }

    // Xoá danh mục
    @GetMapping("/delete")
    public String deleteDanhMuc(@RequestParam("id") Integer id) {
        danhMucRepo.deleteById(id);
        return "redirect:/danh-muc/hien-thi";
    }
}
