package com.example.SmartPhoneHup.DuAn.repository;

import com.example.SmartPhoneHup.DuAn.model.CoAo;
import com.example.SmartPhoneHup.DuAn.model.DuongMay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuongMayRepo extends JpaRepository<DuongMay,Integer> {
}
