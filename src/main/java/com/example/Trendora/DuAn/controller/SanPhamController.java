package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.Cart;
import com.example.Trendora.DuAn.repository.KichThuocRepo;
import com.example.Trendora.DuAn.repository.MauSacRepo;
import com.example.Trendora.DuAn.repository.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("san-pham")
@Controller
public class SanPhamController {
   @Autowired
   SanPhamRepo sanPhamRepo;

   @Autowired
   KichThuocRepo kichThuocRepo;

   @Autowired
   MauSacRepo mauSacRepo;


   @GetMapping("trang-chu")
   public String HienThiTrangChu( Model model) {
      model.addAttribute("list", sanPhamRepo.findAll());
      return "/ViewSanPham2/trang-chu";
   }




   @GetMapping("hien-thi")
   public String HienThiDanhSachSanPham(Model model){
      model.addAttribute("list",sanPhamRepo.findAll());
      return "/ViewSanPham2/hien-thi";
   }


   @GetMapping("/deltal")
   public String ShowChitietSanPham(@RequestParam("id")Integer id , Model model){
      model.addAttribute("list",sanPhamRepo.findAll());
      model.addAttribute("sp",sanPhamRepo.findById(id).get());
      return "/ViewSanPham2/deltal";
   }


//   @GetMapping("/timkiem")
//   public String timKiemSanPham(@RequestParam(value = "ten", required = false, defaultValue = "") String ten, Model model) {
//      List<SanPham> danhSachSanPham = sanPhamRepo.findByTenContainingIgnoreCase(ten);
//      model.addAttribute("list", danhSachSanPham);
//      model.addAttribute("ten", ten); // Giữ lại giá trị tìm kiếm trên giao diện
//      return "ViewSanPham2/hien-thi";
//   }
}
