package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gym_chi_tiet_hoa_don")
public class ChiTietHoaDonE {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_hoa_don")
    private int maChiTietHoaDon;

    // Thiết lập mối quan hệ Many-to-One với HoaDonE
    @ManyToOne
    @JoinColumn(name = "ma_hoa_don")
    private HoaDonE hoaDon;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "don_gia")
    private float donGia;

    @Column(name = "tong_gia_tri")
    private float tongGiaTri;
}
