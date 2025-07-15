package com.example.Trendora.DuAn.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDTO {
    private Integer id;
    private String maHd;
    private String tenNhanVien;
    private String tenKhachHang;
    private String tenHinhThucThanhToan;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private String tenGiamGia;
    private LocalDateTime ngayTao;
    private Integer tongTien;
    private Integer trangThai;
}
