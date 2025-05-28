package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuHoaDonRepo extends JpaRepository<LichSuHoaDon,Integer> {
}
