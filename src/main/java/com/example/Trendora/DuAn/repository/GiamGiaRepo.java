package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiamGiaRepo extends JpaRepository<GiamGia,Integer> {
}
