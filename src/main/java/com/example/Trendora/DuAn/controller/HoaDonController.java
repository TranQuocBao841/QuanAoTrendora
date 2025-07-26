package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.Service.HoaDonService;
import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.HoaDon;
import com.example.Trendora.DuAn.model.HoaDonChiTiet;
import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import com.example.Trendora.DuAn.repository.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonService service;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private SanPhamRepo sanPhamRepo;

    @GetMapping("/hien-thi")
    public String hienThi(Model model,
                          @RequestParam(value = "maHd", required = false) String maHd) {
        List<HoaDonDTO> list = service.getAllOrSearch(maHd);
        model.addAttribute("list", list);
        model.addAttribute("search", maHd);
        return "ViewHoaDon/view";
    }


    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id) {
        service.toggleTrangThai(id);
        return "redirect:/hoa-don/hien-thi";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        List<HoaDonChiTietDTO> chiTietList = service.getChiTiet(id);
        Optional<HoaDonDTO> hoaDonOptional = service.timHoaDonDTOTheoId(id);

        if (hoaDonOptional.isPresent()) {
            HoaDonDTO hoaDon = hoaDonOptional.get();
            model.addAttribute("steps", List.of(TrangThaiDonHang.values()));
            model.addAttribute("ct", chiTietList);
            model.addAttribute("hoaDon", hoaDon);
            return "ViewHoaDon/detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy hóa đơn với ID: " + id);
            return "redirect:/hoa-don/hien-thi"; // hoặc URL danh sách hóa đơn của bạn
        }
    }
    @Autowired
    HoaDonRepo hdr;
    @PostMapping("cap-nhat-trang-thai/{id}")
    public String capNhatTrangThai(@PathVariable("id") Integer id,
                                   @RequestParam("trangThaiMoi") TrangThaiDonHang trangThaiMoi) {
        Optional<HoaDon> optional = hdr.findById(id);
        if (optional.isPresent()) {
            HoaDon hoaDon = optional.get();
            hoaDon.setTrangThaiDonHang(trangThaiMoi);
            hdr.save(hoaDon);
        }
        return "redirect:/hoa-don/detail?id=" + id; // quay lại trang detail
    }


    @GetMapping("/huy")
    public String huyDonHangAdmin(@RequestParam("id") Integer id, RedirectAttributes redirect) {
        Optional<HoaDon> optional = hoaDonRepo.findById(id);
        if (optional.isPresent()) {
            HoaDon hd = optional.get();

            // Chỉ hủy được nếu đang là CHƯA XÁC NHẬN
            if (hd.getTrangThaiDonHang() == TrangThaiDonHang.CHO_XAC_NHAN) {

                // Trả hàng về kho
                List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepo.findByHoaDon(hd);
                for (HoaDonChiTiet ct : chiTietList) {
                    SanPham sp = ct.getSanPham();
                    sp.setSoLuong(sp.getSoLuong() + ct.getSoLuong());
                    sanPhamRepo.save(sp);
                }

                // Cập nhật trạng thái sang ĐÃ HỦY
                hd.setTrangThaiDonHang(TrangThaiDonHang.DA_HUY);
                hoaDonRepo.save(hd);

                redirect.addFlashAttribute("success", "✅ Đơn hàng đã được hủy và hoàn kho.");
            } else {
                redirect.addFlashAttribute("error", "❌ Chỉ được hủy đơn chưa xác nhận.");
            }
        } else {
            redirect.addFlashAttribute("error", "❌ Không tìm thấy đơn hàng.");
        }

        return "redirect:/hoa-don/hien-thi";
    }


}
