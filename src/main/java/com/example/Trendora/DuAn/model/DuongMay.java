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
@Table(name = "duong_may")
public class DuongMay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_duong_may")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "duong_may")
    private String tenduongMay;
}
