package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.KieuDang;
import com.example.SmartPhoneHup.DuAn.model.PhongCach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuDangRepo extends JpaRepository<KieuDang,Integer> {
}
