package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.model.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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
            "hd.id, hd.maHd, nv.tenNv, kh.tenKh, hinhThuc.tenHinhThuc, gg.tenGiamGia, hd.ngayTao,hd.diaChiGiaoHang, hd.tongTien, hd.trangThai, hd.trangThaiDonHang, hd.lyDoHuy) " +
            "FROM HoaDon hd " +
            "LEFT JOIN hd.nhanVien nv " +
            "LEFT JOIN hd.khachHang kh " +
            "LEFT JOIN hd.giamGia gg " +
            "LEFT JOIN hd.hinhThucThanhToan hinhThuc " +
            "WHERE (:maHd IS NULL OR hd.maHd LIKE %:maHd%)")
    List<HoaDonDTO> findAllOrSearch(@Param("maHd") String maHd);

    // --- Lọc theo mã hóa đơn và trạng thái (có phân trang) ---
    @Query("SELECT new com.example.Trendora.DuAn.DTO.HoaDonDTO(" +
            "hd.id, hd.maHd, nv.tenNv, kh.tenKh, hinhThuc.tenHinhThuc, gg.tenGiamGia, " +
            "hd.ngayTao, hd.diaChiGiaoHang, hd.tongTien, hd.trangThai, hd.trangThaiDonHang, hd.lyDoHuy) " +
            "FROM HoaDon hd " +
            "LEFT JOIN hd.nhanVien nv " +
            "LEFT JOIN hd.khachHang kh " +
            "LEFT JOIN hd.giamGia gg " +
            "LEFT JOIN hd.hinhThucThanhToan hinhThuc " +
            "WHERE (:maHd IS NULL OR hd.maHd LIKE %:maHd%) " +
            "AND (:tenKhachHang IS NULL OR kh.tenKh LIKE %:tenKhachHang%) " +
            "AND (:trangThaiDonHang IS NULL OR hd.trangThaiDonHang = :trangThaiDonHang)")
    Page<HoaDonDTO> findAllOrSearchAndTrangThai(@Param("maHd") String maHd,
                                                @Param("tenKhachHang") String tenKhachHang,
                                                @Param("trangThaiDonHang") TrangThaiDonHang trangThaiDonHang,
                                                Pageable pageable);



    @Modifying
    @Transactional
    @Query("UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.id = :id")
    void updateTrangThaiById(@Param("id") Integer id, @Param("trangThai") Integer trangThai);
    @Query("SELECT h.trangThai FROM HoaDon h WHERE h.id = :id")
    Integer findTrangThaiById(@Param("id") Integer id);

    List<HoaDon> findByKhachHang_idKh(Integer idKhachHang);

    long countByTrangThaiDonHang(TrangThaiDonHang trangThai);

    @Query("SELECT SUM(h.tongTien) FROM HoaDon h WHERE h.trangThai = 1")
    BigDecimal tongDoanhThuDaThanhToan();


    @Modifying
    @Transactional
    @Query("UPDATE HoaDon h SET h.trangThaiDonHang = :trangThai WHERE h.id IN :ids")
    void updateTrangThaiBulk(@Param("ids") List<Long> ids,
                             @Param("trangThai") TrangThaiDonHang trangThai);
    // Lọc theo trạng thái và mã hóa đơn
    Page<HoaDon> findByMaHdContainingAndTrangThaiDonHang(String maHd, TrangThaiDonHang trangThai, Pageable pageable);

    Page<HoaDon> findByTrangThaiDonHang(TrangThaiDonHang trangThai, Pageable pageable);

    Page<HoaDon> findByMaHdContaining(String maHd, Pageable pageable);
}