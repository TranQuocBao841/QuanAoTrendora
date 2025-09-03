package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.Service.HoaDonService;
import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.GiamGia;
import com.example.Trendora.DuAn.model.HoaDon;
import com.example.Trendora.DuAn.model.HoaDonChiTiet;
import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.repository.GiamGiaRepo;
import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import com.example.Trendora.DuAn.repository.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private GiamGiaRepo giamGiaRepo;

    @GetMapping("/hien-thi")
    public String hienThi(Model model,
                          @RequestParam(value = "maHd", required = false) String maHd,
                          @RequestParam(value = "tenKhachHang", required = false) String tenKhachHang,  // ✅ thêm
                          @RequestParam(value = "trangThaiDonHang", required = false) TrangThaiDonHang trangThaiDonHang,
                          @RequestParam(value = "page", defaultValue = "0") int page) {

        int size = 6; // Mỗi trang 6 hóa đơn

        Page<HoaDonDTO> hoaDonPage = service.getAllOrSearch(maHd, tenKhachHang, trangThaiDonHang, page, size);

        long tongHoaDon = hoaDonRepo.count();
        long daHoanThanh = hoaDonRepo.countByTrangThaiDonHang(TrangThaiDonHang.DA_HOAN_THANH);
        long daHuy = hoaDonRepo.countByTrangThaiDonHang(TrangThaiDonHang.DA_HUY);
        BigDecimal tongDoanhThu = hoaDonRepo.tongDoanhThuDaThanhToan();
        if (tongDoanhThu == null) tongDoanhThu = BigDecimal.ZERO;

        model.addAttribute("tongHoaDon", tongHoaDon);
        model.addAttribute("daHoanThanh", daHoanThanh);
        model.addAttribute("daHuy", daHuy);
        model.addAttribute("tongDoanhThu", tongDoanhThu);

        // Lấy danh sách hóa đơn cho bảng
        List<HoaDon> danhSachHoaDon = hoaDonRepo.findAll();
        model.addAttribute("hoaDonList", danhSachHoaDon);

        model.addAttribute("list", hoaDonPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hoaDonPage.getTotalPages());
        model.addAttribute("search", maHd);
        model.addAttribute("tenKhachHang", tenKhachHang);  // ✅ giữ giá trị đã nhập
        model.addAttribute("trangThaiDonHang", trangThaiDonHang);



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
    public String capNhatTrangThai(@PathVariable("id") Integer id) {
        Optional<HoaDon> optional = hdr.findById(id);
        if (optional.isPresent()) {
            HoaDon hoaDon = optional.get();
            TrangThaiDonHang trangThaiHienTai = hoaDon.getTrangThaiDonHang();
            TrangThaiDonHang trangThaiTiepTheo = trangThaiHienTai.next();

            if (trangThaiTiepTheo != null) {
                hoaDon.setTrangThaiDonHang(trangThaiTiepTheo);
                hdr.save(hoaDon);
            }
        }
        return "redirect:/hoa-don/detail?id=" + id;
    }



    @PostMapping("/huy/{id}")
    public String huyDonHangAdmin(@PathVariable("id") Integer id,
                                  @RequestParam("lyDoHuy") String lyDoHuy,
                                  RedirectAttributes redirect) {
        Optional<HoaDon> optional = hoaDonRepo.findById(id);
        if (optional.isPresent()) {
            HoaDon hd = optional.get();

            if (hd.getTrangThaiDonHang() == TrangThaiDonHang.CHO_XAC_NHAN
                    || hd.getTrangThaiDonHang() == TrangThaiDonHang.DA_XAC_NHAN
                    || hd.getTrangThaiDonHang() == TrangThaiDonHang.DANG_DONG_GOI) {
                // Trả sản phẩm về kho
                List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepo.findByHoaDon(hd);
                for (HoaDonChiTiet ct : chiTietList) {
                    SanPham sp = ct.getSanPham();
                    sp.setSoLuong(sp.getSoLuong() + ct.getSoLuong());
                    sanPhamRepo.save(sp);
                }

                // Hoàn lại voucher nếu có
                if (hd.getGiamGia() != null) {
                    GiamGia giamGia = hd.getGiamGia();
                    giamGia.setSoLuong(giamGia.getSoLuong() + 1);
                    giamGiaRepo.save(giamGia);
                }

                // Cập nhật trạng thái & lý do
                hd.setTrangThaiDonHang(TrangThaiDonHang.DA_HUY);
                hd.setLyDoHuy(lyDoHuy);
                hoaDonRepo.save(hd);

                redirect.addFlashAttribute("success", "✅ Đơn hàng đã được hủy, hoàn kho và voucher (nếu có).");
            } else {
                redirect.addFlashAttribute("error", "❌ Chỉ được hủy đơn chưa xác nhận.");
            }
        } else {
            redirect.addFlashAttribute("error", "❌ Không tìm thấy đơn hàng.");
        }

        return "redirect:/hoa-don/hien-thi";
    }



    @GetMapping("/in")
    public String inHoaDon(@RequestParam("id") Long id, Model model,
                           @RequestParam(value = "redirect", required = false) String redirect) {
        HoaDon hoaDon = hoaDonRepo.findById(Math.toIntExact(id)).orElse(null);
        if (hoaDon == null) {
            model.addAttribute("error", "Không tìm thấy hóa đơn.");
            return "redirect:/hoa-don/hien-thi";
        }

        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepo.findByHoaDon(hoaDon);
        BigDecimal tongTienHang = chiTietList.stream()
                .map(HoaDonChiTiet::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal giamGia = (hoaDon.getGiamGia() != null)
                ? BigDecimal.valueOf(hoaDon.getGiamGia().getGiaTriGiam())
                : BigDecimal.ZERO;

        BigDecimal tongThanhToan = tongTienHang.subtract(giamGia);

        // ✅ Xử lý tiền khách đưa và tiền trả lại mặc định nếu null
        BigDecimal tienKhachDua = BigDecimal.ZERO;
        BigDecimal tienTraLai = BigDecimal.ZERO;

        Object tienKhachDuaRaw = model.asMap().get("tienKhachDua");
        Object tienTraLaiRaw = model.asMap().get("tienTraLai");

        try {
            if (tienKhachDuaRaw != null) {
                tienKhachDua = new BigDecimal(tienKhachDuaRaw.toString());
            }
            if (tienTraLaiRaw != null) {
                tienTraLai = new BigDecimal(tienTraLaiRaw.toString());
            }
        } catch (Exception e) {
            tienKhachDua = BigDecimal.ZERO;
            tienTraLai = BigDecimal.ZERO;
        }

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("chiTietList", chiTietList);
        model.addAttribute("tongTienHang", tongTienHang);
        model.addAttribute("giamGia", giamGia);
        model.addAttribute("tongThanhToan", tongThanhToan);
        model.addAttribute("redirectBack", redirect);
        model.addAttribute("tienKhachDua", tienKhachDua);
        model.addAttribute("tienTraLai", tienTraLai);

        // ✅ Thêm QR nếu hóa đơn chưa thanh toán
        if (hoaDon.getTrangThai() == 0) {
            String qr = "https://img.vietqr.io/image/mb-0912713606-compact2.png"
                    + "?amount=" + hoaDon.getTongTien().longValue()
                    + "&addInfo=Thanh%20toan%20HD%20" + hoaDon.getMaHd()
                    + "&accountName=NGUYEN%20THI%20HA%20LAN";
            model.addAttribute("qrUrl", qr);
        }

        return "ViewBanHang/in-hoa-don";
    }



}