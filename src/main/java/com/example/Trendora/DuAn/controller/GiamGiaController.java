package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.GiamGia;
import com.example.Trendora.DuAn.repository.GiamGiaRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/giam-gia")
public class GiamGiaController {

    @Autowired
    private GiamGiaRepo giamGiaRepo;

    @GetMapping("/hien-thi")
    public String showHienThi(Model model) {
        model.addAttribute("list", giamGiaRepo.findAll());
        return "ViewGiamGia/hien-thi";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("giamGia", new GiamGia());
        return "ViewGiamGia/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("giamGia") GiamGia giamGia,
                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ViewGiamGia/add";
        }
        giamGiaRepo.save(giamGia);
        return "redirect:/giam-gia/hien-thi";
    }


    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Integer id, Model model) {
        GiamGia giamGia = giamGiaRepo.findById(id).orElse(null);
        model.addAttribute("giamGia", giamGia);
        return "ViewGiamGia/detail";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model) {
        GiamGia giamGia = giamGiaRepo.findById(id).orElse(null);
        model.addAttribute("giamGia", giamGia);

        return "ViewGiamGia/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("giamGia") GiamGia giamGia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ViewGiamGia/update";
        }
        giamGiaRepo.save(giamGia);
        return "redirect:/giam-gia/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        giamGiaRepo.deleteById(id);
        return "redirect:/giam-gia/hien-thi";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<GiamGia> list;
        if (keyword == null || keyword.isEmpty()) {
            list = giamGiaRepo.findAll();
        } else {
            list = giamGiaRepo.findByMaGiamGiaContainingIgnoreCaseOrTenGiamGiaContainingIgnoreCase(keyword, keyword);
        }
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "ViewGiamGia/hien-thi"; // đặt đúng tên view bạn đang dùng
    }

}
