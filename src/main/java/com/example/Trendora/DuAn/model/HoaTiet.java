package com.example.SmartPhoneHup.DuAn.model;

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
@Table(name = "hoa_tiet")
public class HoaTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoa_tiet")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "hoa_tiet")
    private String hoaTiet;
}
