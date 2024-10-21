package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gym_goi_tap")
public class GoiTapE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_goi_tap")
    private int maGoiTap;

    @Column(name = "ten_goi_tap", nullable = false)
    private String tenGoiTap;

    @Column(name = "mo_ta_goi_tap")
    private String moTaGoiTap;

    @Column(name = "gia_goi_tap")
    private float giaGoiTap;

    @Column(name = "thoi_han_goi_tap")
    @Temporal(TemporalType.DATE)
    private Date thoiHanGoiTap;

    // One-to-Many with GoiUuDaiE (mapped by "goiTap" field in GoiUuDaiE)
    @OneToMany(mappedBy = "goiTap", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<GoiUuDaiE> goiUuDais;
}
