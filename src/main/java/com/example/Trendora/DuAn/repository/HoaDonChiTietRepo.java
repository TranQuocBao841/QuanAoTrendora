package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.model.HoaDon;
import com.example.Trendora.DuAn.model.HoaDonChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet,Integer> {
    // 1. Tổng doanh thu theo khoảng ngày
    @Query(value = """
        SELECT SUM(hdct.so_luong * hdct.don_gia)
        FROM hoa_don_chi_tiet hdct
        JOIN hoa_don hd ON hdct.id_hoa_don = hd.id_hoa_don
        WHERE hd.trang_thai = 1
          AND CAST(hd.ngay_tao AS DATE) BETWEEN :from AND :to
    """, nativeQuery = true)
    BigDecimal tinhTongDoanhThu(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // 2. Tổng số sản phẩm đã bán
    @Query(value = """
        SELECT SUM(hdct.so_luong)
        FROM hoa_don_chi_tiet hdct
        JOIN hoa_don hd ON hdct.id_hoa_don = hd.id_hoa_don
        WHERE hd.trang_thai = 1
          AND CAST(hd.ngay_tao AS DATE) BETWEEN :from AND :to
    """, nativeQuery = true)
    Integer demSanPhamBanDuoc(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // 3. Top sản phẩm bán chạy (theo tên sản phẩm)
    @Query(value = """
    SELECT 
        sp.ten_san_pham, 
        SUM(hdct.so_luong) AS tong_so_luong, 
        SUM(hdct.so_luong * hdct.don_gia) AS thanh_tien
    FROM hoa_don_chi_tiet hdct
    JOIN hoa_don hd ON hdct.id_hoa_don = hd.id_hoa_don
    JOIN san_pham sp ON hdct.id_sp = sp.id_san_pham
    WHERE hd.trang_thai = 1
      AND CAST(hd.ngay_tao AS DATE) BETWEEN :from AND :to
    GROUP BY sp.ten_san_pham
    ORDER BY tong_so_luong DESC
""", nativeQuery = true)
    List<Object[]> topSanPham(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );


    // 4. Doanh thu theo ngày
    @Query(value = """
        SELECT 
            CAST(hd.ngay_tao AS DATE) AS ngay,
            SUM(hdct.so_luong * hdct.don_gia) AS doanh_thu
        FROM hoa_don_chi_tiet hdct
        JOIN hoa_don hd ON hdct.id_hoa_don = hd.id_hoa_don
        WHERE hd.trang_thai = 1
          AND CAST(hd.ngay_tao AS DATE) BETWEEN :from AND :to
        GROUP BY CAST(hd.ngay_tao AS DATE)
        ORDER BY ngay
    """, nativeQuery = true)
    List<Object[]> doanhThuTheoNgay(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );



    @Query("SELECT new com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO(ct.id, ct.hoaDon.id, ct.maHdct, sp.tenSanPham, ct.donGia, ct.soLuong, ct.thanhTien, ct.trangThai) " +
            "FROM HoaDonChiTiet ct JOIN ct.sanPham sp WHERE ct.hoaDon.id = :hoaDonId")
    List<HoaDonChiTietDTO> findDTOByHoaDonId(@Param("hoaDonId") Integer hoaDonId);



    @Modifying
    @Transactional
    @Query("UPDATE HoaDonChiTiet c SET c.trangThai = :trangThai WHERE c.hoaDon.id = :hoaDonId")
    void updateTrangThaiByHoaDonId(@Param("hoaDonId") Integer hoaDonId, @Param("trangThai") Integer trangThai);


    List<HoaDonChiTiet> findByHoaDon_Id(Integer idHoaDon);

    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);

}
