package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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




    @Query("SELECT sp FROM SanPham sp WHERE sp.tenSanPham = :tenSanPham")
    List<SanPham> findByTenSanPham(@Param("tenSanPham") String tenSanPham);



    @Query("SELECT sp FROM SanPham sp WHERE "
            + "(:#{#categories == null || #categories.isEmpty()} = true OR sp.danhMuc.tendanhMuc IN :categories) AND "
            + "(:#{#colors == null || #colors.isEmpty()} = true OR sp.mauSac.tenmauSac IN :colors) AND "
            + "(:#{#sizes == null || #sizes.isEmpty()} = true OR sp.kichThuoc.tenkichThuoc IN :sizes)")
    List<SanPham> locTheoDanhMucMauSacVaSize(@Param("categories") List<String> categories,
                                             @Param("colors") List<String> colors,
                                             @Param("sizes") List<String> sizes);


    List<SanPham> findTop5ByOrderByGiaDesc();


    @Query("SELECT sp FROM SanPham sp WHERE " +
            "(:tenSanPham IS NULL OR LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :tenSanPham, '%'))) AND " +
            "(:mauSac IS NULL OR sp.mauSac.id = :mauSac) AND " +
            "(:chatLieu IS NULL OR sp.chatLieu.id = :chatLieu) AND " +
            "(:kichThuoc IS NULL OR sp.kichThuoc.id = :kichThuoc) AND " +
            "(:danhMuc IS NULL OR sp.danhMuc.id = :danhMuc) AND " +
            "(:giaMin IS NULL OR sp.gia >= :giaMin) AND " +
            "(:giaMax IS NULL OR sp.gia <= :giaMax)")
    Page<SanPham> locSanPhamPhanTrang(
            @Param("tenSanPham") String tenSanPham,
            @Param("mauSac") Long mauSac,
            @Param("chatLieu") Long chatLieu,
            @Param("kichThuoc") Long kichThuoc,
            @Param("danhMuc") Long danhMuc,
            @Param("giaMin") BigDecimal giaMin,
            @Param("giaMax") BigDecimal giaMax,
            Pageable pageable
    );



    @Query("SELECT s FROM SanPham s WHERE " +
            "(:keyword IS NULL OR s.tenSanPham LIKE %:keyword%) AND " +
            "(:danhMucId IS NULL OR s.danhMuc.id = :danhMucId) AND " +
            "(:mauSac IS NULL OR s.mauSac.id = :mauSac) AND " +
            "(:kichThuoc IS NULL OR s.kichThuoc.id = :kichThuoc)")
    List<SanPham> timKiemSanPhamNangCao(@Param("keyword") String keyword,
                                        @Param("danhMucId") Integer danhMucId,
                                        @Param("mauSac") Integer mauSac,
                                        @Param("kichThuoc") Integer kichThuoc);


}
