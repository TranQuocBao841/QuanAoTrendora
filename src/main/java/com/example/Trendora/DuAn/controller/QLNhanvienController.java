package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.model.NhanVien;
import com.example.Trendora.DuAn.model.TaiKhoan;
import com.example.Trendora.DuAn.repository.NhanVienRepo;
import com.example.Trendora.DuAn.repository.TaiKhoanRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("nhan-vien/")
public class QLNhanvienController {
    @Autowired
    private NhanVienRepo nvr;

    @Autowired
    TaiKhoanRepo taiKhoanRepo;

    @GetMapping("hien-thi")
    public String hienthi(
            Model model
    ){
        model.addAttribute("list", nvr.findAll());
        return ("ViewNhanvien/view");
    }
    @GetMapping("detail")
    public String detail(
            @RequestParam("id") Integer id, Model model
    ){
        model.addAttribute("nv", nvr.findById(id).get());
        return ("ViewNhanvien/detail");
    }
    @GetMapping("delete")
    public String delete(
            @RequestParam("id") Integer id
    ){
        nvr.deleteById(id);
        return ("redirect:/nhan-vien/hien-thi");
    }
    @GetMapping("viewupdate")
    public String viewupdate(
            @RequestParam("id") Integer id, Model model
    ){
        model.addAttribute("nhanVien", nvr.findById(id).get());
        return ("ViewNhanvien/update");
    }

    @GetMapping("viewadd")
    public String showForm(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        return "ViewNhanVien/add";
    }


    @PostMapping("update")
    public String update(NhanVien nv){
        nvr.save(nv);
        return ("redirect:/nhan-vien/hien-thi");
    }
    @PostMapping("add")
    public String add(@ModelAttribute("nhanVien") @Valid NhanVien nv,
                      BindingResult result,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "ViewNhanVien/add"; // quay lại trang thêm nếu có lỗi
        }

        // Lưu nhân viên
        nv.setTrangThai(1); // ví dụ mặc định nhân viên đang hoạt động
        nvr.save(nv);

        // Sau khi lưu nhân viên thì tạo tài khoản cho nhân viên đó
        TaiKhoan tk = new TaiKhoan();
        tk.setTenDangNhap(nv.getEmail());  // có thể dùng mã NV hoặc email
        tk.setMatKhau("123456");          // mật khẩu mặc định, bắt đổi sau
        tk.setEmail(nv.getEmail());
        tk.setTrangThai(true);
        tk.setLoaiTaiKhoan(1);            // 1 = nhân viên
        tk.setNhanVien(nv);               // gắn quan hệ tới nhân viên

        taiKhoanRepo.save(tk);

        redirectAttributes.addFlashAttribute("success",
                "Thêm nhân viên thành công và đã tạo tài khoản mặc định!");
        return "redirect:/nhan-vien/hien-thi";
    }


}
