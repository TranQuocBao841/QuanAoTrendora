package com.example.Trendora.DuAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kieu_dang")
public class KieuDang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kieu_dang")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "kieu_dang")
    private String tenkieuDang;
}

