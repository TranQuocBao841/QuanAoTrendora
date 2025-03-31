package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.NhanVien;
import com.example.SmartPhoneHup.DuAn.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,Integer> {
    public List<SanPham>findByTenContainingIgnoreCase(String ten);
}
