package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gym_goi_uu_dai")
public class GoiUuDaiE {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ma_goi_uu_dai")
     private int maGoiUuDai;

     @Column(name = "ma_goi_tap", nullable = false)
     private int maGoiTap;

     @Column(name = "ma_uu_dai", nullable = false)
     private int maUuDai;

}
