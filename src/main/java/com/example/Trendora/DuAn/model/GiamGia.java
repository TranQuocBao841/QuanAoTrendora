package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "giam_gia")
public class GiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gg")
    private Integer id;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Column(name = "ma_giam_gia", nullable = false)
    private String maGiamGia;

    @NotBlank(message = "Tên giảm giá không được để trống")
    @Column(name = "ten_giam_gia", nullable = false)
    private String tenGiamGia;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDateTime ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDateTime ngayKetThuc;

    @NotBlank(message = "Loại giảm giá không được để trống")
    @Column(name = "loai_giam_gia", nullable = false)
    private String loaiGiamGia;

    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
    @Column(name = "gia_tri_giam", nullable = false)
    private Integer giaTriGiam;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}
