package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.DTO.DanhGiaDTO;
import com.example.Trendora.DuAn.model.*;
import com.example.Trendora.DuAn.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

   @Autowired
   ChatLieuRepo chatLieuRepo;
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

      // Nếu có lọc thì không dùng phân trang
      boolean coLoc = (categories != null && !categories.isEmpty()) ||
              (colors != null && !colors.isEmpty()) ||
              (sizes != null && !sizes.isEmpty());

      if (coLoc) {
         list = sanPhamRepo.locTheoDanhMucMauSacVaSize(categories, colors, sizes);
         model.addAttribute("list", list);
         model.addAttribute("currentPage", 0); // Không phân trang khi lọc
         model.addAttribute("totalPages", 1);
      } else {
         Pageable pageable = PageRequest.of(page, 9);
         Page<SanPham> pageSanPham = sanPhamRepo.findAll(pageable);
         list = pageSanPham.getContent();
         model.addAttribute("list", list);
         model.addAttribute("currentPage", page);
         model.addAttribute("totalPages", pageSanPham.getTotalPages());
      }

      // Các dữ liệu khác
      model.addAttribute("danhMucList", danhMucRepo.findAll());
      model.addAttribute("mauSacList", mauSacRepo.findAll());
      model.addAttribute("kichthuocList", kichThuocRepo.findAll());
      model.addAttribute("top5SanPham", sanPhamRepo.findTop5ByOrderByGiaDesc());

      // Giữ lại lựa chọn bộ lọc
      model.addAttribute("selectedCategories", categories != null ? categories : List.of());
      model.addAttribute("selectedColors", colors != null ? colors : List.of());
      model.addAttribute("selectedSizes", sizes != null ? sizes : List.of());

      return "/ViewSanPham2/hien-thi";
   }




   @Autowired
   DanhGiaRepository dr;

   @GetMapping("/deltal")
   public String showChiTietSanPham(@RequestParam("id") Integer id, Model model) {
      SanPham sp = sanPhamRepo.findById(id).orElse(null);
      if (sp == null) {
         return "redirect:/san-pham/hien-thi";
      }

      // Lấy các biến thể cùng tên (nếu đúng logic), hoặc cùng cha sản phẩm
      List<SanPham> bienTheList = sanPhamRepo.findByMaSanPham(sp.getMaSanPham());

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
      List<DanhGia> danhGiaList = dr.findBySanPham_Id(id);
      double trungBinhSao = 0;
      if (!danhGiaList.isEmpty()) {
         double tongSao = danhGiaList.stream().mapToInt(DanhGia::getSoSao).sum();
         trungBinhSao = tongSao / danhGiaList.size();
      }
      model.addAttribute("trungBinhSao", trungBinhSao);
      model.addAttribute("idsp", id);
      model.addAttribute("danhGiaList", danhGiaList);
      model.addAttribute("sp", sp);
      model.addAttribute("bienTheList", bienTheList);
      model.addAttribute("listMauSac", listMauSac);
      model.addAttribute("listKichThuoc", listKichThuoc);
      model.addAttribute("listChatLieu", listChatLieu);
      model.addAttribute("list", sanPhamRepo.findAll());
      return "/ViewSanPham2/deltal";
   }

   @PostMapping("/them")
   public String themDanhGia(@ModelAttribute DanhGiaDTO danhGiaDTO,
                             @RequestParam("idSanPham") Integer sanPhamId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

      TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("khachHangDangNhap");
      if (taiKhoan == null || taiKhoan.getKhachHang() == null) {
         redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để đánh giá.");
         return "redirect:/quan-ao/login";
      }

      Optional<SanPham> optionalSanPham = sanPhamRepo.findById(sanPhamId);
      if (optionalSanPham.isEmpty()) {
         redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại.");
         return "redirect:/";
      }

      DanhGia dg = new DanhGia();
      dg.setSanPham(optionalSanPham.get());
      dg.setKhachHang(taiKhoan.getKhachHang());
      dg.setSoSao(danhGiaDTO.getSoSao());
      dg.setBinhLuan(danhGiaDTO.getBinhLuan());
      dg.setThoiGian(LocalDateTime.now());

      dr.save(dg);

      redirectAttributes.addFlashAttribute("success", "Đánh giá đã được gửi.");
      return "redirect:/san-pham/deltal?id=" + sanPhamId;
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

   @GetMapping("/loc-nang-cao")
   public String locSanPhamNangCao(
           @RequestParam(required = false) String tenSanPham,
           @RequestParam(required = false) Long mauSac,
           @RequestParam(required = false) Long chatLieu,
           @RequestParam(required = false) Long kichThuoc,
           @RequestParam(required = false) Long danhMuc,
           @RequestParam(required = false) String gia,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(required = false) String sort,
           Model model
   ) {
      BigDecimal giaMin = null;
      BigDecimal giaMax = null;

      // Xử lý khoảng giá từ chuỗi
      if (gia != null) {
         switch (gia) {
            case "duoi-500.000":
               giaMax = new BigDecimal(500000);
               break;
            case "500.000-1.000.000":
               giaMin = new BigDecimal(500000);
               giaMax = new BigDecimal(1000000);
               break;
            case "1.000.000-3.000.000":
               giaMin = new BigDecimal(1000000);
               giaMax = new BigDecimal(5000000);
               break;
            case "tren-5.000.000":
               giaMin = new BigDecimal(5000000);
               break;
         }
      }

      // Xử lý sắp xếp theo giá
      Pageable pageable;
      if ("asc".equals(sort)) {
         pageable = PageRequest.of(page, 6, Sort.by("gia").ascending());
      } else if ("desc".equals(sort)) {
         pageable = PageRequest.of(page, 6, Sort.by("gia").descending());
      } else {
         pageable = PageRequest.of(page, 6);
      }

      // Gọi hàm truy vấn có phân trang từ DB
      Page<SanPham> pageSanPham = sanPhamRepo.locSanPhamPhanTrang(
              tenSanPham, mauSac, chatLieu, kichThuoc, danhMuc,
              giaMin, giaMax, pageable
      );

      // Gửi dữ liệu sản phẩm
      model.addAttribute("list", pageSanPham.getContent());
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", pageSanPham.getTotalPages());

      // Gửi dữ liệu filter đã chọn (để giữ lại sau khi lọc)
      model.addAttribute("tenSanPham", tenSanPham);
      model.addAttribute("selectedMauSac", mauSac);
      model.addAttribute("selectedChatLieu", chatLieu);
      model.addAttribute("selectedKichThuoc", kichThuoc);
      model.addAttribute("selectedDanhMuc", danhMuc);
      model.addAttribute("selectedGia", gia);
      model.addAttribute("selectedSort", sort);

      // Gửi danh sách để hiển thị dropdown
      model.addAttribute("mauSacList", mauSacRepo.findAll());
      model.addAttribute("chatLieuList", chatLieuRepo.findAll());
      model.addAttribute("kichthuocList", kichThuocRepo.findAll());
      model.addAttribute("danhMucList", danhMucRepo.findAll());
      // hiển thị lại top 5 sản phẩm
      model.addAttribute("top5SanPham", sanPhamRepo.findTop5ByOrderByGiaDesc());


      return "/ViewSanPham2/hien-thi"; // View hiển thị sản phẩm
   }
}
