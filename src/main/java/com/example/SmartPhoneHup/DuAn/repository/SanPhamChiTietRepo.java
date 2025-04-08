package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.SanPham;
import com.example.SmartPhoneHup.DuAn.model.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepo extends JpaRepository<SanPhamChiTiet,Integer> {
    List<SanPhamChiTiet> findAllBySanPham_Id(Integer sanPhamId);

}
