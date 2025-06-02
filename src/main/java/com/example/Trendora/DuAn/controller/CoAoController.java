package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.ChatLieu;
import com.example.Trendora.DuAn.model.CoAo;
import com.example.Trendora.DuAn.repository.ChatLieuRepo;
import com.example.Trendora.DuAn.repository.CoAoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/co-ao")
@Controller
public class CoAoController {
   @Autowired
   CoAoRepo coAoRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("listcoAo",coAoRepo.findAll());
        return "/ViewCoAo/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewCoAo/add";
    }

    @PostMapping("/add")
    public String add(CoAo coAo){
        coAoRepo.save(coAo);
        return "redirect:/co-ao/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",coAoRepo.findById(id).get());
        model.addAttribute("listcoAo",coAoRepo.findAll());
        return "/ViewCoAo/update";
    }

    @PostMapping("/update")
    public String Update(CoAo coAo){
        coAoRepo.save(coAo);
        return "redirect:/co-ao/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        coAoRepo.deleteById(id);
        return "redirect:/co-ao/hien-thi";
    }
}
