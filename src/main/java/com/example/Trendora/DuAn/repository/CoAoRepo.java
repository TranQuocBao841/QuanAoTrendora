package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.CoAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoAoRepo extends JpaRepository<CoAo,Integer> {
}
