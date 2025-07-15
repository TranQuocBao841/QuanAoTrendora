package com.example.Trendora.DuAn.Service;

import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.DTO.HoaDonDTO;
import com.example.Trendora.DuAn.repository.HoaDonChiTietRepo;
import com.example.Trendora.DuAn.repository.HoaDonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonService {
    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    public List<HoaDonDTO> getAllOrSearch(String maHd) {
        if (maHd != null && maHd.trim().isEmpty()) {
            maHd = null;
        }
        return hoaDonRepo.findAllOrSearch(maHd);
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
                    dto.setTenHinhThucThanhToan(hd.getHinhThucThanhToan().getTenHinhThuc()); // nếu cần
                    return dto;
                });
    }
}




