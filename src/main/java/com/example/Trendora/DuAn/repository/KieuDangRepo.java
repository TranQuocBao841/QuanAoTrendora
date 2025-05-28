package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuDangRepo extends JpaRepository<KieuDang,Integer> {
}
