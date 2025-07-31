package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.NhanVien;
import com.example.Trendora.DuAn.repository.NhanVienRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("nhan-vien/")
public class QLNhanvienController {
    @Autowired
    private NhanVienRepo nvr;
    @GetMapping("hien-thi")
    public String hienthi(
            Model model
    ){
        model.addAttribute("list", nvr.findAll());
        return ("ViewNhanvien/view");
    }
    @GetMapping("detail")
    public String detail(
            @RequestParam("id") Integer id, Model model
    ){
        model.addAttribute("nv", nvr.findById(id).get());
        return ("ViewNhanvien/detail");
    }
    @GetMapping("delete")
    public String delete(
            @RequestParam("id") Integer id
    ){
        nvr.deleteById(id);
        return ("redirect:/nhan-vien/hien-thi");
    }
    @GetMapping("viewupdate")
    public String viewupdate(
            @RequestParam("id") Integer id, Model model
    ){
        model.addAttribute("nhanVien", nvr.findById(id).get());
        return ("ViewNhanvien/update");
    }

    @GetMapping("viewadd")
    public String showForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        return "ViewNhanVien/add";
    }


    @PostMapping("update")
    public String update(NhanVien nv){
        nvr.save(nv);
        return ("redirect:/nhan-vien/hien-thi");
    }
    @PostMapping("add")
    public String add(@ModelAttribute("nhanVien") @Valid NhanVien nv,
                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ViewNhanVien/add"; // quay lại trang thêm nếu có lỗi
        }

        nvr.save(nv);
        return "redirect:/nhan-vien/hien-thi";
    }

}
