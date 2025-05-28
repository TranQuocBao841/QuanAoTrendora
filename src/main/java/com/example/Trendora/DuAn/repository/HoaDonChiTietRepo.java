package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet,Integer> {
}
