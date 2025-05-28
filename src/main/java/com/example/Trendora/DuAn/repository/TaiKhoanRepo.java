package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaiKhoanRepo extends JpaRepository<TaiKhoan,Integer> {
//    TaiKhoan findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);
    boolean existsByTenDangNhap(String tenDangNhap);

    boolean existsByEmail(String email);


    TaiKhoan findByEmailAndMatKhau(String email, String matKhau);

    Optional<TaiKhoan> findByTenDangNhap(String tenDangNhap);
}
