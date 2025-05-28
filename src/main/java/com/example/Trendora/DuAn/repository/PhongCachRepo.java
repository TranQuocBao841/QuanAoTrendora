package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.PhongCach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongCachRepo extends JpaRepository<PhongCach,Integer> {
}
