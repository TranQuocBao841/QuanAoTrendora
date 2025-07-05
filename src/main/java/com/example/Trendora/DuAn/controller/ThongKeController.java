package com.example.Trendora.DuAn.controller;

import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/admin/thong-ke")
public class ThongKeController {

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepository;

    @GetMapping("hien-thi")
    public String thongKe(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            Model model
    ) {
        if (fromDate == null) fromDate = LocalDate.now().withDayOfMonth(1);
        if (toDate == null) toDate = LocalDate.now();

        BigDecimal tongDoanhThu = hoaDonChiTietRepository.tinhTongDoanhThu(fromDate, toDate);
        Integer soHoaDon = hoaDonRepository.demHoaDon(fromDate, toDate);
        Integer soLuongSanPham = hoaDonChiTietRepository.demSanPhamBanDuoc(fromDate, toDate);
        List<Object[]> topSanPham = hoaDonChiTietRepository.topSanPham(fromDate, toDate);
        List<Object[]> doanhThuTheoNgay = hoaDonChiTietRepository.doanhThuTheoNgay(fromDate, toDate);

        // Đưa dữ liệu từ query vào map để tiện lookup
        Map<LocalDate, BigDecimal> doanhThuMap = new HashMap<>();
        for (Object[] obj : doanhThuTheoNgay) {
            LocalDate ngay = ((Date) obj[0]).toLocalDate();

            // ✅ Sửa lỗi ép kiểu ở đây
            Number doanhThuNumber = (Number) obj[1];
            BigDecimal doanhThu = BigDecimal.valueOf(doanhThuNumber.doubleValue());

            doanhThuMap.put(ngay, doanhThu);
        }

        // Tạo list đầy đủ các ngày từ fromDate đến toDate
        List<String> ngayList = new ArrayList<>();
        List<BigDecimal> doanhThuList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            ngayList.add(date.format(formatter));
            doanhThuList.add(doanhThuMap.getOrDefault(date, BigDecimal.ZERO));
        }

        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("tongDoanhThu", tongDoanhThu);
        model.addAttribute("soHoaDon", soHoaDon);
        model.addAttribute("soLuongSanPham", soLuongSanPham);
        model.addAttribute("topSanPham", topSanPham);
        model.addAttribute("ngayList", ngayList);
        model.addAttribute("doanhThuList", doanhThuList);

        return "ViewThongKe/hien-thi";
    }

}
