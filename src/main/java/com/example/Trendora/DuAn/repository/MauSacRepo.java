package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepo extends JpaRepository<MauSac,Integer> {
}
