package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DangKyE {
    private int maDangKy;
    private int maThanhVien;
    private int maGoiUuDai;
    private Date ngayDangKy;
    private Date ngayKichHoat;
    private boolean trangThaiDangKy;
    private int maChiNhanh;
    private int maLopHoc;
}
