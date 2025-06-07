package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "danh_muc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_danh_muc")
    private Integer id;

    @NotBlank(message = "Mã danh mục không được để trống")
    @Column(name = "ma_danh_muc")
    private String ma;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Column(name = "ten_danh_muc")
    private String tendanhMuc;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trang_thai")
    private Integer trangThai;

}

