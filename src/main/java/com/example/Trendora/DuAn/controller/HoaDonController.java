package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonService service;

    @GetMapping("/hien-thi")
    public String hienThi(Model model,
                          @RequestParam(value = "maHd", required = false) String maHd) {
        List<HoaDonDTO> list = service.getAllOrSearch(maHd);
        model.addAttribute("list", list);
        model.addAttribute("search", maHd);
        return "ViewHoaDon/view";
    }


    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id) {
        service.toggleTrangThai(id);
        return "redirect:/hoa-don/hien-thi";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("ct", service.getChiTiet(id).orElse(null));
        return "ViewHoaDon/detail";
    }
}
