package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "danh_gia")
public class DanhGia {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "so_sao", nullable = false)
    private Integer soSao;

    @Column(name = "binh_luan")
    private String binhLuan;

    @Column(name = "anh")
    private String anh;

    @Column(name = "thoi_gian", nullable = false)
    private LocalDateTime thoiGian;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPham sanPham;


}

