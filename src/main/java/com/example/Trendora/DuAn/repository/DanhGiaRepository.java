package com.example.Trendora.DuAn.repository;
import com.example.Trendora.DuAn.model.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer>{
    List<DanhGia> findBySanPham_Id(Integer id);
}
