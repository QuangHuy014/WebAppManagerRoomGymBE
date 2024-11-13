package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DangKyDTO {

    private Integer maDangKy;
    private int maThanhVien;
    private Integer maGoiUuDai;
    private Date ngayDangKy;
    private Date ngayKichHoat;
    private boolean trangThaiDangKy;
    private int maGoiTap;
    private Integer maLopHoc;
//    private int maHoaDon;

}
