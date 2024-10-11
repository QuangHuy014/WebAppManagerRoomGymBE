package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "gym_dang_ky")
public class DangKyE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDangKy;

    @ManyToOne
    @JoinColumn(name = "ma_thanh_vien")
    private ThanhVienE thanhVien;

    @ManyToOne
    @JoinColumn(name = "ma_goi_uu_dai")
    private GoiUuDaiE goiUuDai;

    private Date ngayDangKy;
    private Date ngayKichHoat;
    private boolean trangThaiDangKy;


    @ManyToOne
    @JoinColumn(name = "ma_lop_hoc")
    private LopHocE lopHoc;
}
