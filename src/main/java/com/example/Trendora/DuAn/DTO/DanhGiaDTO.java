package com.example.Trendora.DuAn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DanhGiaDTO {
    private Integer soSao;
    private String binhLuan;
    private LocalDateTime thoiGian;
    private String tenKhachHang;
    private String tenSanPham;
}
