package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "gym_thanh_vien")
public class ThanhVienE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maThanhVien;

    private String tenThanhVien;
    private String emailThanhVien;
    private String matKhauNguoiDung;
    private String soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private String duLieuQrDinhDanh;

    @ManyToOne
    @JoinColumn(name = "ma_nguoi_dung")
    private NguoiDungE nguoiDung;
}
