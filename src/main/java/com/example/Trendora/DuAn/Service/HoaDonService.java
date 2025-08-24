package com.example.Trendora.DuAn.Service;

import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.enums.TrangThaiDonHang;
import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;

@Service
public class HoaDonService {
    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    public Page<HoaDonDTO> getAllOrSearch(String maHd,
                                          String tenKhachHang,   // ✅ thêm
                                          TrangThaiDonHang trangThaiDonHang,
                                          int page, int size) {
        if (maHd != null && maHd.trim().isEmpty()) {
            maHd = null;
        }
        if (tenKhachHang != null && tenKhachHang.trim().isEmpty()) {
            tenKhachHang = null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ngayTao"));

        return hoaDonRepo.findAllOrSearchAndTrangThai(maHd, tenKhachHang, trangThaiDonHang, pageable);
    }




    public List<HoaDonChiTietDTO> getChiTiet(Integer hoaDonId) {
        return hoaDonChiTietRepo.findDTOByHoaDonId(hoaDonId);
    }


    @Transactional
    public void toggleTrangThai(Integer hoaDonId) {
        // Lấy trạng thái hiện tại
        Integer currentTrangThai = hoaDonRepo.findTrangThaiById(hoaDonId); // Tạo method này

        int newStatus = currentTrangThai == 0 ? 1 : 0;

        hoaDonRepo.updateTrangThaiById(hoaDonId, newStatus);
        hoaDonChiTietRepo.updateTrangThaiByHoaDonId(hoaDonId, newStatus);
    }

    public Optional<HoaDonDTO> timHoaDonDTOTheoId(Integer id) {
        return hoaDonRepo.findById(id)
                .map(hd -> {
                    HoaDonDTO dto = new HoaDonDTO();
                    dto.setId(hd.getId());
                    dto.setMaHd(hd.getMaHd());
                    dto.setNgayTao(hd.getNgayTao());
                    dto.setTenKhachHang(hd.getKhachHang().getTenKh());
                    dto.setTrangThai(hd.getTrangThai());
                    dto.setTongTien(hd.getTongTien());
                    dto.setTenHinhThucThanhToan(hd.getHinhThucThanhToan().getTenHinhThuc());
                    dto.setTrangThaiDonHang(hd.getTrangThaiDonHang());
                    return dto;
                });
    }

}




