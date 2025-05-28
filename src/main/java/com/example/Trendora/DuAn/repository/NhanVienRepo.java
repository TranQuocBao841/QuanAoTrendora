package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien,Integer> {
    NhanVien findByEmail(String email);
}
