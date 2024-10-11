package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "gym_goi_tap")
public class GoiTapE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
