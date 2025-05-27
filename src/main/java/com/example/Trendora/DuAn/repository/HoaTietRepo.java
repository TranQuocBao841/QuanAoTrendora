package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.HoaTiet;
import com.example.SmartPhoneHup.DuAn.model.KhuyAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaTietRepo extends JpaRepository<HoaTiet,Integer> {
}
