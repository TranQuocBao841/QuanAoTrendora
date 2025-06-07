package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepo extends JpaRepository<DanhMuc,Integer> {
}
