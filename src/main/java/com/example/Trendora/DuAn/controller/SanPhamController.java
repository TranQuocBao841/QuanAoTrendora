package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.*;
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
import java.util.Set;
import java.util.stream.Collectors;

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
   public String showChiTietSanPham(@RequestParam("id") Integer id, Model model) {
      SanPham sp = sanPhamRepo.findById(id).orElse(null);
      if (sp == null) {
         return "redirect:/san-pham/hien-thi";
      }

      // Lấy các biến thể cùng tên (nếu đúng logic), hoặc cùng cha sản phẩm
      List<SanPham> bienTheList = sanPhamRepo.findByTenSanPham(sp.getTenSanPham());

      // Lọc các thuộc tính duy nhất
      List<MauSac> listMauSac = bienTheList.stream()
              .map(SanPham::getMauSac)
              .distinct()
              .toList();

      List<KichThuoc> listKichThuoc = bienTheList.stream()
              .map(SanPham::getKichThuoc)
              .distinct()
              .toList();

      List<ChatLieu> listChatLieu = bienTheList.stream()
              .map(SanPham::getChatLieu)
              .distinct()
              .toList();

      // Gửi dữ liệu sang view
      model.addAttribute("sp", sp);
      model.addAttribute("bienTheList", bienTheList);
      model.addAttribute("listMauSac", listMauSac);
      model.addAttribute("listKichThuoc", listKichThuoc);
      model.addAttribute("listChatLieu", listChatLieu);
      model.addAttribute("list", sanPhamRepo.findAll());
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
