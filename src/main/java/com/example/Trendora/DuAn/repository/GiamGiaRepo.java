package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiamGiaRepo extends JpaRepository<GiamGia,Integer> {
    List<GiamGia> findByMaGiamGiaContainingIgnoreCaseOrTenGiamGiaContainingIgnoreCase(String ma, String ten);

    @Query("SELECT g FROM GiamGia g WHERE g.trangThai = 1 AND g.ngayBatDau <= :now AND g.ngayKetThuc >= :now")
    List<GiamGia> findMaGiamGiaConHieuLuc(@Param("now") LocalDateTime now);

    Optional<GiamGia> findByMaGiamGia(String maGiamGia);

}
