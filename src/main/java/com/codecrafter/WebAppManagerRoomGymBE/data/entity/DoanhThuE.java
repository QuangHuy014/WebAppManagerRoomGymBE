package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gym_doanh_thu")
public class DoanhThuE {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_doanh_thu")
    private int maDoanhThu;

    @Column
    private String loaiThoiGianDoanhThu;


    @Column(name = "so_tien_doanh_thu")
    private double soTienDoanhThu;

    @Column(name = "ngay_tao_doanh_thu")
    private Date ngayTaoDoanhThu;


    @OneToMany(mappedBy = "doanhThu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<HoaDonE> hoaDonS;


}
