package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "san_pham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_san_pham")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_co_ao")
    private CoAo coAo;

    @ManyToOne
    @JoinColumn(name = "id_khuy_ao")
    private KhuyAo khuyAo;

    @ManyToOne
    @JoinColumn(name = "id_kieu_dang")
    private KieuDang kieuDang;

    @ManyToOne
    @JoinColumn(name = "id_duong_may")
    private DuongMay duongMay;

    @ManyToOne
    @JoinColumn(name = "id_hoa_tiet")
    private HoaTiet hoaTiet;

    @ManyToOne
    @JoinColumn(name = "id_phong_cach")
    private PhongCach phongCach;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_kich_thuoc")
    private KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "id_tay_ao")
    private TayAo tayAo;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "ma_san_pham")
    private String maSanPham;

    @Column(name = "anh_san_pham")
    private String anhSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "gia")
    private BigDecimal gia;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay")
    private LocalDate ngay;
}

