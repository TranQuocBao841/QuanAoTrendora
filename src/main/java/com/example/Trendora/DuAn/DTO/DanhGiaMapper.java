package com.example.Trendora.DuAn.DTO;

import com.example.Trendora.DuAn.model.DanhGia;

public class DanhGiaMapper {

    public static DanhGiaDTO toDTO(DanhGia danhGia) {
        DanhGiaDTO dto = new DanhGiaDTO();
        dto.setSoSao(danhGia.getSoSao());
        dto.setBinhLuan(danhGia.getBinhLuan());
        dto.setThoiGian(danhGia.getThoiGian());
        dto.setTenKhachHang(danhGia.getKhachHang().getTenKh());
        dto.setTenSanPham(danhGia.getSanPham().getTenSanPham());
        return dto;
    }
}
