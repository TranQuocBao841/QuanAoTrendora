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
@Table(name = "phong_cach")
public class PhongCach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phong_cach")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "phong_cach")
    private String tenphongCach;
}

