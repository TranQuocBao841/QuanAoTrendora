package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.PhongCach;
import com.example.Trendora.DuAn.model.TayAo;
import com.example.Trendora.DuAn.repository.PhongCachRepo;
import com.example.Trendora.DuAn.repository.TayAoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/tay-ao")
@Controller
public class TayAoController {
   @Autowired
   TayAoRepo tayAoRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("list",tayAoRepo.findAll());
        return "/ViewTayAo/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewTayAo/add";
    }

    @PostMapping("/add")
    public String add(TayAo tayAo){
        tayAoRepo.save(tayAo);
        return "redirect:/tay-ao/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",tayAoRepo.findById(id).get());
        return "/ViewTayAo/update";
    }

    @PostMapping("/update")
    public String Update(TayAo tayAo){
        tayAoRepo.save(tayAo);
        return "redirect:/tay-ao/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        tayAoRepo.deleteById(id);
        return "redirect:/tay-ao/hien-thi";
    }
}
