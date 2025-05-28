package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.DuongMay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuongMayRepo extends JpaRepository<DuongMay,Integer> {
}
