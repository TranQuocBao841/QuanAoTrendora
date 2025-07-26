package com.example.Trendora.DuAn.model;

import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoa_don")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_nv")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_kh")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_gg")
    private GiamGia giamGia;

    @ManyToOne
    @JoinColumn(name = "id_hinh_thuc")
    private HinhThucThanhToan hinhThucThanhToan;

    @Column(name = "ma_hd")
    private String maHd;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "tong_tien")
    private Integer tongTien;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "diaChiGiaoHang")
    private String diaChiGiaoHang;


    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai_don_hang")
    private TrangThaiDonHang trangThaiDonHang;
}




