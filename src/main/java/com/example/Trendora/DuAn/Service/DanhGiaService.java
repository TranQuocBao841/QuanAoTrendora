package com.example.Trendora.DuAn.Service;

import com.example.Trendora.DuAn.DTO.DanhGiaDTO;
import com.example.Trendora.DuAn.DTO.DanhGiaMapper;
import com.example.Trendora.DuAn.model.DanhGia;
import com.example.Trendora.DuAn.repository.DanhGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DanhGiaService {
    @Autowired
    private DanhGiaRepository dr;
    public List<DanhGiaDTO> getDanhGiaBySanPhamId(Integer sanPhamId) {
        List<DanhGia> danhGias = dr.findBySanPham_Id(sanPhamId);
        return danhGias.stream()
                .map(DanhGiaMapper::toDTO)
                .collect(Collectors.toList());
    }

}
