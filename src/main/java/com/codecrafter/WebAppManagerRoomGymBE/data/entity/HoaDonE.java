package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gym_hoa_don")
public class HoaDonE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private int maHoaDon;

    @Column(name = "ngay_tao_hoa_don")
    private Date ngayTaoHoaDon;

    @Column(name = "so_tien_thanh_toan")
    private float soTienThanhToan;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DangKyE> dangkys;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ThanhToanE> thanhToans;

    @Transient
    private int tongHoaDon;
}
