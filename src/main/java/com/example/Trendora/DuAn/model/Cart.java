package com.example.Trendora.DuAn.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();

    public void addItem(CartItem item) {
        if (items.containsKey(item.getIdSpChiTiet())) {
            CartItem existing = items.get(item.getIdSpChiTiet());
            existing.setSoLuong(existing.getSoLuong() + item.getSoLuong());
        } else {
            items.put(item.getIdSpChiTiet(), item);
        }
    }

    public void removeItem(Integer idSpChiTiet) {
        items.remove(idSpChiTiet);
    }

    public Collection<CartItem> getAllItems() {
        return items.values();
    }

    public BigDecimal getTongTien() {
        return items.values().stream()
                .map(CartItem::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
    }

    public void updateItem(CartItem item) {
        if (items.containsKey(item.getIdSpChiTiet())) {
            CartItem existing = items.get(item.getIdSpChiTiet());
            existing.setSoLuong(item.getSoLuong());
        }
    }

    public CartItem getItemById(Integer idSpChiTiet) {
        return items.get(idSpChiTiet);
    }

    // Getter/Setter nếu cần
}

