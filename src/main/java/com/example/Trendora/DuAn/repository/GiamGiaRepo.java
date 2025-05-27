package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.ChatLieu;
import com.example.SmartPhoneHup.DuAn.model.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiamGiaRepo extends JpaRepository<GiamGia,Integer> {
}
