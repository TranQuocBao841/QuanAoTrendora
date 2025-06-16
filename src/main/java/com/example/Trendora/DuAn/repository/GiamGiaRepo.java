package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiamGiaRepo extends JpaRepository<GiamGia,Integer> {
    List<GiamGia> findByMaGiamGiaContainingIgnoreCaseOrTenGiamGiaContainingIgnoreCase(String ma, String ten);
}
