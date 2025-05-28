package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hdct")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_sp")
    private SanPham sanPham;

    @Column(name = "ma_hdct")
    private String maHdct;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "thanh_tien")
    private BigDecimal thanhTien;

    @Column(name = "trang_thai")
    private Integer trangThai;
}


