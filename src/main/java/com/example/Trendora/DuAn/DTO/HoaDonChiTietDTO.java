package com.example.Trendora.DuAn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietDTO {
    private Integer id;
    private Integer idHoaDon;
    private String maHdct;
    private String tenSanPham;
    private Integer donGia;
    private Integer soLuong;
    private Integer thanhTien;
    private Integer trangThai;
}

