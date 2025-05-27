package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.GiamGia;
import com.example.SmartPhoneHup.DuAn.model.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhThucThanhToanRepo extends JpaRepository<HinhThucThanhToan,Integer> {
}
