package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LichSuTapLuyenE {
    private int maLichSuTapLuyen;
    private int maThanhVien;
    private Date thoiGianTapLuyen;
    private String ghiChuTapLuyen;
}
