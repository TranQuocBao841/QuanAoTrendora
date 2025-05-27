package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.MauSac;
import com.example.SmartPhoneHup.DuAn.model.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TayAoRepo extends JpaRepository<TayAo,Integer> {
}
