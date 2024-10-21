package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

@Data
@Entity
@Table(name = "gym_dang_ky")
public class DangKyE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ma_dang_ky")
    private int maDangKy;

    @ManyToOne
    @JoinColumn(name = "ma_thanh_vien")
    private ThanhVienE thanhVien;

    @ManyToOne
    @JoinColumn(name = "ma_goi_uu_dai")
    private GoiUuDaiE goiUuDai;

    @JoinColumn(name = "ngay_dang_ky")
    private Date ngayDangKy;

    @JoinColumn(name = "ngay_kich_hoat")
    private Date ngayKichHoat;

    @JoinColumn(name = "trang_thai_dang_ky")
    private boolean trangThaiDangKy;

    @ManyToOne
    @JoinColumn(name = "ma_lop_hoc")
    private LopHocE lopHoc;

    // Many-to-One relationship with HoaDonE
    @ManyToOne
    @JoinColumn(name = "ma_hoa_don")
    private HoaDonE hoaDon;
}
