package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.HoaTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaTietRepo extends JpaRepository<HoaTiet,Integer> {
}
