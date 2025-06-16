package com.example.Trendora.DuAn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    private Integer id;           // id của SanPham
    private String tenSanPham;
    private String mauSac;        // để null nếu không có
    private String kichThuoc;     // để null nếu không có
    private int soLuong;
    private BigDecimal donGia;
    private String anh;

    public CartItem(Integer id, String tenSanPham, String mauSac, String kichThuoc,
                    int soLuong, BigDecimal donGia, String anh) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.anh = anh;
    }

    public BigDecimal getThanhTien() {
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }

    // Getter & Setter cho tất cả các thuộc tính
}

