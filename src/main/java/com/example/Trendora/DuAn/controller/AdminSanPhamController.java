package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.KhachHang;
import com.example.SmartPhoneHup.DuAn.model.KhuyAo;
import com.example.SmartPhoneHup.DuAn.model.SanPham;
import com.example.SmartPhoneHup.DuAn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("hien-thi")
    public String Hienthi(Model model){
        model.addAttribute("list",sanPhamRepo.findAll());
        return "ViewSanpham/hien-thi";
    }


    @GetMapping("/view-add")
    public String viewAddForm(Model model) {
        model.addAttribute("sp", new SanPham());
        model.addAttribute("dsMauSac",mauSacRepo.findAll());
        model.addAttribute("dsKichThuoc",kichThuocRepo.findAll());
        model.addAttribute("dsChatLieu",chatLieuRepo.findAll());
        model.addAttribute("dsCoAo",coAoRepo.findAll());
        model.addAttribute("dsDuongMay",duongMayRepo.findAll());
        model.addAttribute("dsKhuyAo",khuyAoRepo.findAll());
        model.addAttribute("dsKieuDang",kieuDangRepo.findAll());
        model.addAttribute("dsPhongCach",phongCachRepo.findAll());
        model.addAttribute("dsTayAo",tayAoRepo.findAll());
        model.addAttribute("dsHoaTiet",hoaTietRepo.findAll());
        return "/ViewSanPham/add";
    }

    @PostMapping("/add")
    public String add(SanPham sanPham){
        sanPhamRepo.save(sanPham);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/deltal")
    public String showDeltal(@RequestParam("id") Integer id , Model model){
        model.addAttribute("sp",sanPhamRepo.findById(id).get());
        return "/ViewSanPham/deltal";
    }

    @GetMapping("/update")
    public String showUpdate(@RequestParam("id") Integer id ,Model model){
        model.addAttribute("sp",sanPhamRepo.findById(id).get());
        model.addAttribute("dsMauSac",mauSacRepo.findAll());
        model.addAttribute("dsKichThuoc",kichThuocRepo.findAll());
        model.addAttribute("dsChatLieu",chatLieuRepo.findAll());
        model.addAttribute("dsCoAo",coAoRepo.findAll());
        model.addAttribute("dsDuongMay",duongMayRepo.findAll());
        model.addAttribute("dsKhuyAo",khuyAoRepo.findAll());
        model.addAttribute("dsKieuDang",kieuDangRepo.findAll());
        model.addAttribute("dsPhongCach",phongCachRepo.findAll());
        model.addAttribute("dsTayAo",tayAoRepo.findAll());
        model.addAttribute("dsHoaTiet",hoaTietRepo.findAll());
        return "/ViewSanPham/update";
    }

    @PostMapping("/update")
    public String update(SanPham sanPham){
        sanPhamRepo.save(sanPham);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
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
}
