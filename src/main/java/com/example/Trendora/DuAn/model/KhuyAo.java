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
@Table(name = "khuy_ao")
public class KhuyAo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khuy_ao")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "khuy_ao")
    private String tenkhuyAo;
}


