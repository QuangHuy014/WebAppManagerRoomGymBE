package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gym_uu_dai")
public class UuDaiE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_uu_dai")
    private int maUuDai;

    @Column(name = "mo_ta_uu_dai", nullable = false)
    private String moTaUuDai;

    @Column(name = "ngay_bat_dau_uu_dai")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDauUuDai;

    @Column(name = "ngay_ket_thuc_uu_dai")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThucUuDai;

    @Column(name = "gia_tri_uu_dai")
    private float giaTriUuDai;

    @Column(name = "trang_thai_uu_dai", nullable = false)
    private boolean trangThaiUuDai;

    @OneToMany(mappedBy = "uuDai", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<GoiUuDaiE> goiUuDais;
}
