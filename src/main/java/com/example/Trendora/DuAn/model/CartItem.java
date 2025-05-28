package com.example.Trendora.DuAn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    private Integer idSpChiTiet;
    private String tenSanPham;
    private String mauSac;
    private String kichThuoc;
    private int soLuong;
    private BigDecimal donGia;
    private String anh;


    public BigDecimal getThanhTien() {
        return donGia.multiply(new BigDecimal(soLuong));
    }


}

