package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.DanhMucRepo;
import com.example.Trendora.DuAn.repository.KichThuocRepo;
import com.example.Trendora.DuAn.repository.MauSacRepo;
import com.example.Trendora.DuAn.repository.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

   @Autowired
   DanhMucRepo danhMucRepo;

   @GetMapping("trang-chu")
   public String HienThiTrangChu( Model model) {
      model.addAttribute("list", sanPhamRepo.findAll());
      return "/ViewSanPham2/trang-chu";
   }




   @GetMapping("hien-thi")
   public String HienThiDanhSachSanPham(
           @RequestParam(value = "categories", required = false) List<String> categories,
           @RequestParam(value = "colors", required = false) List<String> colors,
           @RequestParam(value = "sizes", required = false) List<String> sizes,
           @RequestParam(defaultValue = "0") int page,
           Model model) {

      List<SanPham> list;

      // Lọc theo danh mục, màu sắc, kích thước nếu có chọn
      if ((categories != null && !categories.isEmpty()) ||
              (colors != null && !colors.isEmpty()) ||
              (sizes != null && !sizes.isEmpty())) {

         list = sanPhamRepo.locTheoDanhMucMauSacVaSize(categories, colors, sizes);
      } else {
         list = sanPhamRepo.findAll();
      }

      // Gửi dữ liệu ra view
      model.addAttribute("list", list); // danh sách sản phẩm hiển thị
      model.addAttribute("danhMucList", danhMucRepo.findAll()); // danh mục
      model.addAttribute("mauSacList", mauSacRepo.findAll());   // màu sắc
      model.addAttribute("kichthuocList", kichThuocRepo.findAll()); // kích thước
      model.addAttribute("top5SanPham", sanPhamRepo.findTop5ByOrderByGiaDesc());

      // Giữ lại giá trị đã chọn sau khi lọc
      model.addAttribute("selectedCategories", categories != null ? categories : List.of());
      model.addAttribute("selectedColors", colors != null ? colors : List.of());
      model.addAttribute("selectedSizes", sizes != null ? sizes : List.of());

      Pageable pageable = PageRequest.of(page, 6); // 5 sản phẩm mỗi trang
      Page<SanPham> pageSanPham = sanPhamRepo.findAll(pageable);

      model.addAttribute("list", pageSanPham.getContent());
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", pageSanPham.getTotalPages());
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

   @GetMapping("/loc-theo-danh-muc")
   public String locTheoDanhMuc(
           @RequestParam(value = "categories", required = false) List<String> categories,
           @RequestParam(value = "colors", required = false) List<String> colors,
           @RequestParam(value = "sizes", required = false) List<String> sizes,
           Model model) {

      List<SanPham> filteredList = sanPhamRepo.locTheoDanhMucMauSacVaSize(categories, colors,sizes);

      model.addAttribute("sanPhamList", filteredList);
      model.addAttribute("selectedCategories", categories != null ? categories : List.of());
      model.addAttribute("selectedColors", colors != null ? colors : List.of());
      model.addAttribute("selectedSizes", sizes != null ? sizes : List.of());
      return "ViewSanPham2/hien-thi :: productList"; // chỉ render lại danh sách sản phẩm
   }

//   @GetMapping("/timkiem")
//   public String timKiemSanPham(@RequestParam(value = "ten", required = false, defaultValue = "") String ten, Model model) {
//      List<SanPham> danhSachSanPham = sanPhamRepo.findByTenContainingIgnoreCase(ten);
//      model.addAttribute("list", danhSachSanPham);
//      model.addAttribute("ten", ten); // Giữ lại giá trị tìm kiếm trên giao diện
//      return "ViewSanPham2/hien-thi";
//   }
}
