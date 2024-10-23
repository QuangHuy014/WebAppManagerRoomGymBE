package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

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
    private List<ThanhToanE> thanhToans;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<ChiTietHoaDonE> chiTietHoaDons;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<DangKyE> dangKys;

    @ManyToOne
    @JoinColumn(name = "ma_doanh_thu")
    private DoanhThuE doanhThu;

}
