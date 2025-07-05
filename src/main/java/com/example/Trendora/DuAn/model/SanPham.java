package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    // ============ Validate các trường bắt buộc ============
    @NotNull(message = "Cổ áo không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_co_ao")
    private CoAo coAo;

    @NotNull(message = "Khuy áo không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_khuy_ao")
    private KhuyAo khuyAo;

    @NotNull(message = "Kiểu dáng không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_kieu_dang")
    private KieuDang kieuDang;

    @NotNull(message = "Đường may không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_duong_may")
    private DuongMay duongMay;

    @NotNull(message = "Họa tiết không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_hoa_tiet")
    private HoaTiet hoaTiet;

    @NotNull(message = "Phong cách không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_phong_cach")
    private PhongCach phongCach;

    @NotNull(message = "Chất liệu không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chatLieu;

    @NotNull(message = "Màu sắc không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @NotNull(message = "Kích thước không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_kich_thuoc")
    private KichThuoc kichThuoc;

    @NotNull(message = "Danh mục không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_danh_muc")
    private DanhMuc danhMuc;


    @NotNull(message = "Tay áo không được để trống")
    @ManyToOne
    @JoinColumn(name = "id_tay_ao")
    private TayAo tayAo;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Column(name = "ma_san_pham", unique = true)
    private String maSanPham;

    @Column(name = "anh_san_pham")
    private String anhSanPham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    @Column(name = "so_luong")
    private Integer soLuong;

    @NotBlank(message = "Mô tả không được để trống")
    @Column(name = "mo_ta")
    private String moTa;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Column(name = "gia")
    private BigDecimal gia;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trang_thai")
    private Integer trangThai;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày không được để trống")
    @Column(name = "ngay")
    private LocalDate ngay;
}


