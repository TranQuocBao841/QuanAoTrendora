package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.KhuyAo;
import com.example.SmartPhoneHup.DuAn.model.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuyAoRepo extends JpaRepository<KhuyAo,Integer> {
}
