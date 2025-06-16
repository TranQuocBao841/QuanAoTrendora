package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kh")
    private Integer idKh;

    @NotBlank(message = "Mã khách hàng không được để trống")
    @Column(name = "ma_kh")
    private String maKh;

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Column(name = "ten_kh")
    private String tenKh;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trang_thai")
    private Integer trangThai;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "dia_chi")
    private String diaChi;

    @Pattern(regexp = "^0\\d{9,10}$", message = "SĐT không hợp lệ")
    @Column(name = "sdt")
    private String sdt;

    @NotNull(message = "Giới tính không được để trống")
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải nhỏ hơn ngày hiện tại")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @NotBlank(message = "Quốc tịch không được để trống")
    @Column(name = "quoc_tich")
    private String quocTich;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(name = "email")
    private String email;
}




