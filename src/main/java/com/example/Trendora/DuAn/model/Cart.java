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
