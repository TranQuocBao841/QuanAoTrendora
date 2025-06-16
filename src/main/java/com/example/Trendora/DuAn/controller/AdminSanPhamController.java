package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin/san-pham")
@Controller
public class AdminSanPhamController {
    @Autowired
    SanPhamRepo sanPhamRepo;

    @Autowired
    MauSacRepo mauSacRepo;

    @Autowired
    KichThuocRepo kichThuocRepo;

    @Autowired
    ChatLieuRepo chatLieuRepo;

    @Autowired
    CoAoRepo coAoRepo;

    @Autowired
    DuongMayRepo duongMayRepo;

    @Autowired
    KhuyAoRepo khuyAoRepo;

    @Autowired
    KieuDangRepo kieuDangRepo;

    @Autowired
    PhongCachRepo phongCachRepo;

    @Autowired
    TayAoRepo tayAoRepo;

    @Autowired
    HoaTietRepo hoaTietRepo;

    @Autowired
    DanhMucRepo danhMucRepo;

    @GetMapping("hien-thi")
    public String Hienthi(Model model) {
        model.addAttribute("list", sanPhamRepo.findAll());
        return "ViewSanpham/hien-thi";
    }


    @GetMapping("/view-add")
    public String viewAddForm(Model model) {
        model.addAttribute("sp", new SanPham());
        model.addAttribute("dsMauSac", mauSacRepo.findAll());
        model.addAttribute("dsKichThuoc", kichThuocRepo.findAll());
        model.addAttribute("dsChatLieu", chatLieuRepo.findAll());
        model.addAttribute("dsCoAo", coAoRepo.findAll());
        model.addAttribute("dsDuongMay", duongMayRepo.findAll());
        model.addAttribute("dsKhuyAo", khuyAoRepo.findAll());
        model.addAttribute("dsKieuDang", kieuDangRepo.findAll());
        model.addAttribute("dsPhongCach", phongCachRepo.findAll());
        model.addAttribute("dsTayAo", tayAoRepo.findAll());
        model.addAttribute("dsHoaTiet", hoaTietRepo.findAll());
        model.addAttribute("dsDanhMuc", danhMucRepo.findAll());
        return "/ViewSanPham/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("sp") SanPham sp,
                      BindingResult result,
                      Model model) {

        if (sanPhamRepo.existsByMaSanPham(sp.getMaSanPham())) {
            result.rejectValue("maSanPham", null, "Mã sản phẩm đã tồn tại");
        }

        if (result.hasErrors()) {
            model.addAttribute("dsChatLieu", chatLieuRepo.findAll());
            model.addAttribute("dsMauSac", mauSacRepo.findAll());
            model.addAttribute("dsKichThuoc", kichThuocRepo.findAll());
            model.addAttribute("dsCoAo", coAoRepo.findAll());
            model.addAttribute("dsDuongMay", duongMayRepo.findAll());
            model.addAttribute("dsHoaTiet", hoaTietRepo.findAll());
            model.addAttribute("dsKhuyAo", khuyAoRepo.findAll());
            model.addAttribute("dsKieuDang", kieuDangRepo.findAll());
            model.addAttribute("dsPhongCach", phongCachRepo.findAll());
            model.addAttribute("dsTayAo", tayAoRepo.findAll());
            model.addAttribute("dsDanhMuc", danhMucRepo.findAll());
            return "ViewSanPham/add";
        }
        sanPhamRepo.save(sp);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/deltal")
    public String showDeltal(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("sp", sanPhamRepo.findById(id).get());
        return "/ViewSanPham/deltal";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("sp", sanPhamRepo.findById(id).get());
        model.addAttribute("dsMauSac", mauSacRepo.findAll());
        model.addAttribute("dsKichThuoc", kichThuocRepo.findAll());
        model.addAttribute("dsChatLieu", chatLieuRepo.findAll());
        model.addAttribute("dsCoAo", coAoRepo.findAll());
        model.addAttribute("dsDuongMay", duongMayRepo.findAll());
        model.addAttribute("dsKhuyAo", khuyAoRepo.findAll());
        model.addAttribute("dsKieuDang", kieuDangRepo.findAll());
        model.addAttribute("dsPhongCach", phongCachRepo.findAll());
        model.addAttribute("dsTayAo", tayAoRepo.findAll());
        model.addAttribute("dsHoaTiet", hoaTietRepo.findAll());
        model.addAttribute("dsDanhMuc", danhMucRepo.findAll());
        return "/ViewSanPham/update";
    }

    @PostMapping("/update")
    public String update(SanPham sanPham) {
        sanPhamRepo.save(sanPham);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        sanPhamRepo.deleteById(id);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/timkiem")
    public String timKiemSanPham(@RequestParam(value = "ten", required = false, defaultValue = "") String ten, Model model) {
        List<SanPham> danhSachSanPham = sanPhamRepo.findSanPhamsByTenSanPhamContains(ten);
        model.addAttribute("list", danhSachSanPham);
        model.addAttribute("ten", ten); // Giữ lại giá trị tìm kiếm trên giao diện
        return "ViewSanPham/hien-thi";
    }

    @GetMapping("/loc")
    public String locSanPham(
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Integer trangThai,
            @RequestParam(required = false) Long mauSac,
            @RequestParam(required = false) Long kichThuoc,
            @RequestParam(required = false) Long danhMuc,
            @RequestParam(required = false) BigDecimal giaTu,
            @RequestParam(required = false) BigDecimal giaDen,
            Model model) {

        List<SanPham> all = sanPhamRepo.findAll();

        List<SanPham> filtered = all.stream()
                .filter(sp -> tenSanPham == null || sp.getTenSanPham().toLowerCase().contains(tenSanPham.toLowerCase()))
                .filter(sp -> trangThai == null || sp.getTrangThai().equals(trangThai))
                .filter(sp -> mauSac == null || (sp.getMauSac() != null && sp.getMauSac().getId().equals(mauSac)))
                .filter(sp -> kichThuoc == null || (sp.getKichThuoc() != null && sp.getKichThuoc().getId().equals(kichThuoc)))
                .filter(sp -> danhMuc == null || (sp.getDanhMuc() != null && sp.getDanhMuc().getId().equals(danhMuc)))
                .filter(sp -> giaTu == null || (sp.getGia() != null && sp.getGia().compareTo(giaTu) >= 0))
                .filter(sp -> giaDen == null || (sp.getGia() != null && sp.getGia().compareTo(giaDen) <= 0))
                .collect(Collectors.toList());

        model.addAttribute("dsSanPham", filtered);

        // Gửi danh sách để hiển thị dropdown
        model.addAttribute("dsMauSac", mauSacRepo.findAll());
        model.addAttribute("dsKichThuoc", kichThuocRepo.findAll()); // sửa đúng tên repo
        model.addAttribute("dsDanhMuc", danhMucRepo.findAll());

        return "ViewSanPham/hien-thi";
    }




}