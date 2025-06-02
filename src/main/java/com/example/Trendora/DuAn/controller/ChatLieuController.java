package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.ChatLieu;
import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.repository.ChatLieuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/chat-lieu")
@Controller
public class ChatLieuController {
   @Autowired
   ChatLieuRepo chatLieuRepo;

    @GetMapping("/hien-thi")
    public String HienThi(Model model){
        model.addAttribute("listcl",chatLieuRepo.findAll());
        return "/ViewChatLieu/hien-thi";
    }

    @GetMapping("/add")
    public String showthem(){
        return "/ViewChatLieu/add";
    }

    @PostMapping("/add")
    public String add(ChatLieu chatLieu){
        chatLieuRepo.save(chatLieu);
        return "redirect:/chat-lieu/hien-thi";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model){
        model.addAttribute("list",chatLieuRepo.findById(id).get());
        model.addAttribute("listcl",chatLieuRepo.findAll());
        return "/ViewChatLieu/update";
    }

    @PostMapping("/update")
    public String Update(ChatLieu chatLieu){
        chatLieuRepo.save(chatLieu);
        return "redirect:/chat-lieu/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        chatLieuRepo.deleteById(id);
        return "redirect:/chat-lieu/hien-thi";
    }
}
