package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon,Integer> {
    @Query(value = "SELECT COUNT(*) FROM hoa_don WHERE ngay_tao BETWEEN :from AND :to AND trang_thai = 1", nativeQuery = true)
    Integer demHoaDon(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
