package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhThucThanhToanRepo extends JpaRepository<HinhThucThanhToan,Integer> {
}
