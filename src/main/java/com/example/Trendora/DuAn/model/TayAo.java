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
@Table(name = "tay_ao")
public class TayAo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tay_ao")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "tay_ao")
    private String tentayAo;
}



