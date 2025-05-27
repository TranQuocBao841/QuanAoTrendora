package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.HinhThucThanhToan;
import com.example.SmartPhoneHup.DuAn.model.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuHoaDonRepo extends JpaRepository<LichSuHoaDon,Integer> {
}
