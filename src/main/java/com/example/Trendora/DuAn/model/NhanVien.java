package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nv")
    private Integer idNv;

    @NotBlank(message = "Mã nhân viên không được để trống")
    @Column(name = "ma_nv")
    private String maNv;

    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(name = "ten_nv")
    private String tenNv;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải có 10 chữ số")
    @Column(name = "sdt")
    private String sdt;

    @NotBlank(message = "Số CCCD không được để trống")
    @Column(name = "cccd")
    private String cccd;

    @NotNull(message = "Vui lòng chọn giới tính")
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @NotNull(message = "Ngày sinh không được để trống")
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "dia_chi")
    private String diaChi;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trang_thai")
    private Integer trangThai;
}





