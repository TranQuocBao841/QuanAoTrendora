package com.example.SmartPhoneHup.DuAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hinh_thuc_thanh_toan")
public class HinhThucThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hinh_thuc")
    private Integer id;

    @Column(name = "ten_hinh_thuc")
    private String tenHinhThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;
}



