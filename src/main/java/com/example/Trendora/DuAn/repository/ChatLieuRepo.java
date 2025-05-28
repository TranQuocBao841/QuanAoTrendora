package com.example.Trendora.DuAn.repository;

import com.example.Trendora.DuAn.model.ChatLieu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuRepo extends JpaRepository<ChatLieu,Integer> {
}
