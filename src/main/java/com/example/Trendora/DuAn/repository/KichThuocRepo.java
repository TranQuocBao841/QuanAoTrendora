package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KichThuocRepo extends JpaRepository<KichThuoc,Integer> {
}
