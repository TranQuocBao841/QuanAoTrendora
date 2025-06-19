package com.example.Trendora.DuAn.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();


    public void addItem(CartItem item) {
        if (items.containsKey(item.getId())) {
            CartItem existing = items.get(item.getId());
            existing.setSoLuong(existing.getSoLuong() + item.getSoLuong());
        } else {
            items.put(item.getId(), item);
        }
    }

    public void removeItem(Integer idSanPham) {
        items.remove(idSanPham);
    }

    // ✅ Trả về danh sách item
    public Collection<CartItem> getAllItems() {
        return items.values();
    }

    // ✅ Tính tổng tiền từ tất cả CartItem
    public BigDecimal getTongTien() {
        return items.values().stream()
                .map(CartItem::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public BigDecimal getTongTienTruocGiam() {
        return getTongTien();
    }

    public BigDecimal getTongTienSauGiam(GiamGia giamGia) {
        if (giamGia == null) return getTongTien();

        BigDecimal tongTien = getTongTien();
        BigDecimal tienGiam = BigDecimal.ZERO;

        String loai = giamGia.getLoaiGiamGia().toLowerCase();

        if (loai.contains("phần") || loai.contains("phan")) {
            // phần trăm
            tienGiam = tongTien.multiply(BigDecimal.valueOf(giamGia.getGiaTriGiam()))
                    .divide(BigDecimal.valueOf(100));
        } else {
            // tiền mặt
            tienGiam = BigDecimal.valueOf(giamGia.getGiaTriGiam());
        }

        BigDecimal ketQua = tongTien.subtract(tienGiam);
        return ketQua.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : ketQua;
    }

    public void clear() {
        items.clear();
    }

    public void updateItem(CartItem item) {
        if (items.containsKey(item.getId())) {
            CartItem existing = items.get(item.getId());
            existing.setSoLuong(item.getSoLuong());
        }
    }



    public CartItem getItemById(Integer idSanPham) {
        return items.get(idSanPham);
    }

    // ✅ Getter cho items nếu cần
    public Map<Integer, CartItem> getItems() {
        return items;
    }

}
