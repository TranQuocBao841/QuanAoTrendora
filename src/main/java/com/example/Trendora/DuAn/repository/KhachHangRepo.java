package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepo extends JpaRepository<KhachHang,Integer> {
   KhachHang findByEmail(String email);

   List<KhachHang> findByMaKhContainingIgnoreCase(String maKh);
}
