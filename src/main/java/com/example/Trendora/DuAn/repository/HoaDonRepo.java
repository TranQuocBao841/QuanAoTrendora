package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon,Integer> {
}
