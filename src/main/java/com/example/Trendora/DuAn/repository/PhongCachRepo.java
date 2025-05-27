package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.PhongCach;
import com.example.SmartPhoneHup.DuAn.model.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongCachRepo extends JpaRepository<PhongCach,Integer> {
}
