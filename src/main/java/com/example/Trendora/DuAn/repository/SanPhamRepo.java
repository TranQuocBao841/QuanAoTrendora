package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,Integer> {
    public List<SanPham>findSanPhamsByTenSanPhamContains(String ten);
}
