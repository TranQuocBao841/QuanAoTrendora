package com.example.Trendora.DuAn.enums;

public enum TrangThaiDonHang {
    CHO_XAC_NHAN("Chờ xác nhận"),
    DA_XAC_NHAN("Đã xác nhận"),
    DANG_DONG_GOI("Đang đóng gói"),
    DANG_VAN_CHUYEN("Đang vận chuyển"),
    DA_GIAO("Đã giao"),
    DA_HOAN_THANH("Đã hoàn thành"),
    DA_HUY("Đã huỷ");

    private final String moTa;

    TrangThaiDonHang(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }
}
