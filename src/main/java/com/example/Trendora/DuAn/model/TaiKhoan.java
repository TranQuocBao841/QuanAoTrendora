package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tai_khoan")
    private Integer idTaiKhoan;

    @ManyToOne
    @JoinColumn(name = "id_nv")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_kh")
    private KhachHang khachHang;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "loai_tai_khoan")
    private Integer loaiTaiKhoan;

    @Column(name = "trang_thai")
    private Integer trangThai;
}





