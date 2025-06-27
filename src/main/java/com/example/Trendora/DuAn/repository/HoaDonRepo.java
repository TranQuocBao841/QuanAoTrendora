package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.model.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon,Integer> {
    @Query(value = "SELECT COUNT(*) FROM hoa_don WHERE ngay_tao BETWEEN :from AND :to AND trang_thai = 1", nativeQuery = true)
    Integer demHoaDon(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );


    @Query("SELECT new com.example.Trendora.DuAn.DTO.HoaDonDTO(" +
            "hd.id, hd.maHd, nv.tenNv, kh.tenKh, gg.tenGiamGia, hinhThuc.tenHinhThuc, hd.ngayTao, hd.tongTien, hd.trangThai) " +
            "FROM HoaDon hd " +
            "LEFT JOIN hd.nhanVien nv " +
            "LEFT JOIN hd.khachHang kh " +
            "LEFT JOIN hd.giamGia gg " +
            "LEFT JOIN hd.hinhThucThanhToan hinhThuc " +
            "WHERE (:maHd IS NULL OR hd.maHd LIKE %:maHd%)")
    List<HoaDonDTO> findAllOrSearch(@Param("maHd") String maHd);
    @Modifying
    @Transactional
    @Query("UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.id = :id")
    void updateTrangThaiById(@Param("id") Integer id, @Param("trangThai") Integer trangThai);
    @Query("SELECT h.trangThai FROM HoaDon h WHERE h.id = :id")
    Integer findTrangThaiById(@Param("id") Integer id);
}
