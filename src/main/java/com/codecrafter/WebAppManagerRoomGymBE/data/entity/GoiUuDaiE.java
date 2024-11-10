package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "gym_goi_uu_dai")
public class GoiUuDaiE {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ma_goi_uu_dai")
     private int maGoiUuDai;

     @ManyToOne
     @JoinColumn(name = "ma_goi_tap", nullable = false)
     private GoiTapE goiTap;

     @ManyToOne
     @JoinColumn(name = "ma_uu_dai", nullable = true)
     private UuDaiE uuDai;

     @OneToMany(fetch = FetchType.EAGER, mappedBy = "goiUuDai")
     @JsonBackReference
     private List<DangKyE> dangKy;
}
