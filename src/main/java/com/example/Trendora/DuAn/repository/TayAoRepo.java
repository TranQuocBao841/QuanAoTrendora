package com.example.Trendora.DuAn.repository;


import com.example.Trendora.DuAn.model.TayAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TayAoRepo extends JpaRepository<TayAo,Integer> {
}
