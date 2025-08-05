package com.example.Trendora.DuAn.Service;

import com.example.Trendora.DuAn.DTO.HoaDonChiTietDTO;
import com.example.Trendora.DuAn.DTO.HoaDonDTO;
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

    public Page<HoaDonDTO> getAllOrSearch(String maHd, int page, int size) {
        if (maHd != null && maHd.trim().isEmpty()) {
            maHd = null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ngayTao"));

        // Giả sử repo cũ của bạn trả về List<HoaDonDTO>
        List<HoaDonDTO> fullList = hoaDonRepo.findAllOrSearch(maHd);

        // 🔹 Sắp xếp hóa đơn mới lên trên cùng
        fullList.sort((h1, h2) -> h2.getNgayTao().compareTo(h1.getNgayTao()));

        // 🔹 Phân trang thủ công
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), fullList.size());
        List<HoaDonDTO> subList = fullList.subList(start, end);

        return new PageImpl<>(subList, pageable, fullList.size());
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




