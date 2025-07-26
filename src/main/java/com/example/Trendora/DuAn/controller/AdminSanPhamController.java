package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
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

    @GetMapping("/hien-thi")
    public String hienThiSanPham(
            @RequestParam(required = false) String tenSanPham,
            @RequestParam(required = false) Long mauSac,
            @RequestParam(required = false) Long chatLieu,
            @RequestParam(required = false) Long kichThuoc,
            @RequestParam(required = false) String khoangGia,
            @RequestParam(required = false) Long danhMuc,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        //kiểm tra đã đăng nhập hay chưa
        TaiKhoan admin = (TaiKhoan) session.getAttribute("adminDangNhap");
        if (admin == null || admin.getLoaiTaiKhoan() == null || admin.getLoaiTaiKhoan() != 1) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập bằng tài khoản Admin!");
            return "redirect:/quan-ao/login";
        } else {
            // Xử lý khoảng giá nếu có
            BigDecimal giaMin = null;
            BigDecimal giaMax = null;

            if (khoangGia != null && khoangGia.contains("-")) {
                try {
                    String[] parts = khoangGia.split("-");
                    giaMin = new BigDecimal(parts[0]);
                    giaMax = new BigDecimal(parts[1]);
                } catch (Exception e) {
                    // Nếu không parse được giá, cứ để null
                }
            }

            // In log để kiểm tra
            System.out.println("Tên SP: " + tenSanPham);
            System.out.println("Màu sắc ID: " + mauSac);
            System.out.println("Kích thước ID: " + kichThuoc);
            System.out.println("Chất liệu ID: " + chatLieu);
            System.out.println("Khoảng giá: " + giaMin + " - " + giaMax);
            System.out.println("Danh mục ID: " + danhMuc);


            // Sau khi gọi repository trả về Page<SanPham>
            Page<SanPham> sanPhamPage = sanPhamRepo.locSanPhamPhanTrang(
                    tenSanPham,
                    mauSac,
                    chatLieu,
                    kichThuoc,
                    danhMuc,
                    giaMin,
                    giaMax,
                    PageRequest.of(page, size)
            );

            model.addAttribute("list", sanPhamPage.getContent()); // truyền danh sách
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", sanPhamPage.getTotalPages());

            // Đổ dropdown
            model.addAttribute("dsMauSac", mauSacRepo.findAll());
            model.addAttribute("dsKichThuoc", kichThuocRepo.findAll());
            model.addAttribute("dsChatLieu", chatLieuRepo.findAll());
            model.addAttribute("dsDanhMuc", danhMucRepo.findAll());

            // Giữ lại giá trị lọc
            model.addAttribute("tenSanPham", tenSanPham);
            model.addAttribute("mauSac", mauSac);
            model.addAttribute("chatLieu", chatLieu);
            model.addAttribute("kichThuoc", kichThuoc);
            model.addAttribute("khoangGia", khoangGia);
            model.addAttribute("danhMuc", danhMuc);

            return "ViewSanPham/hien-thi";
        }
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
                      @RequestParam("fileAnh") MultipartFile fileAnh,
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

        // ✅ Dùng đường dẫn ngoài project (ổ D)
        String uploadFolder = "D:/ShopImages/";

        if (!fileAnh.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + fileAnh.getOriginalFilename();
                File uploadDir = new File(uploadFolder);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Lưu ảnh vào ổ D
                fileAnh.transferTo(new File(uploadFolder + fileName));

                // Gán đường dẫn để hiển thị ảnh
                sp.setAnhSanPham("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Lỗi khi tải ảnh lên.");
                return "ViewSanPham/add";
            }
        } else {
            model.addAttribute("error", "Vui lòng chọn ảnh sản phẩm.");
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
    public String update(@ModelAttribute("sanPham") SanPham sanPham,
                         @RequestParam("fileAnh") MultipartFile fileAnh,
                         Model model) {
        try {
            // Lấy sản phẩm cũ
            SanPham sanPhamCu = sanPhamRepo.findById(sanPham.getId()).orElse(null);
            if (sanPhamCu == null) {
                model.addAttribute("error", "Không tìm thấy sản phẩm.");
                return "ViewTrendora/sanPham/update";
            }

            // Nếu có upload ảnh mới
            if (!fileAnh.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + fileAnh.getOriginalFilename();
                String uploadDir = "D:/ShopImages/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                fileAnh.transferTo(new File(uploadDir + fileName));

                // Gán tên ảnh mới
                sanPham.setAnhSanPham("/uploads/" + fileName);
            } else {
                // Giữ lại ảnh cũ nếu không upload ảnh mới
                sanPham.setAnhSanPham(sanPhamCu.getAnhSanPham());
            }

            sanPhamRepo.save(sanPham);
            return "redirect:/admin/san-pham/hien-thi";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi cập nhật sản phẩm.");
            return "ViewTrendora/sanPham/update";
        }
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



}