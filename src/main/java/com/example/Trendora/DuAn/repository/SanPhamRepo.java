package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,Integer> {
    public List<SanPham> findSanPhamsByTenSanPhamContains(String ten);

    List<SanPham> findByMoTaContaining(String moTa);

    @Query("SELECT sp FROM SanPham sp WHERE "
            + "(:tenSanPham IS NULL OR LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :tenSanPham, '%'))) AND "
            + "(:trangThai IS NULL OR sp.trangThai = :trangThai) AND "
            + "(:giaTu IS NULL OR sp.gia >= :giaTu) AND "
            + "(:giaDen IS NULL OR sp.gia <= :giaDen) AND "
            + "(:kichThuocId IS NULL OR sp.kichThuoc.id = :kichThuocId) AND "
            + "(:mauSacId IS NULL OR sp.mauSac.id = :mauSacId) AND "
            + "(:danhMucId IS NULL OR sp.danhMuc.id = :danhMucId)")
    List<SanPham> locSanPhamTheoId(
            @Param("tenSanPham") String tenSanPham,
            @Param("trangThai") Integer trangThai,
            @Param("giaTu") BigDecimal giaTu,
            @Param("giaDen") BigDecimal giaDen,
            @Param("kichThuocId") Long kichThuocId,
            @Param("mauSacId") Long mauSacId,
            @Param("danhMucId") Long danhMucId
    );

    boolean existsByMaSanPham(String maSanPham);

    // Tên sản phẩm
    @Query("SELECT DISTINCT s.tenSanPham FROM SanPham s")
    List<String> findDistinctTenSanPham();

    // Kích thước - đảm bảo chỉ lấy chuỗi, không lấy đối tượng
    @Query("SELECT DISTINCT s.kichThuoc.tenkichThuoc FROM SanPham s")
    List<String> findDistinctKichThuocAsString(); // ✅ đúng


    // Màu sắc
    @Query("SELECT DISTINCT s.mauSac FROM SanPham s")
    List<String> findDistinctMauSacAsString();

    // Chất liệu
    @Query("SELECT DISTINCT s.chatLieu FROM SanPham s")
    List<String> findDistinctChatLieuAsString();

}