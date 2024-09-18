package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ThanhToanE {
    private int maThanhToan;
    private int maHoaDon;
    private Date ngayThanhToan;
    private float soTienThanhToan;
    private String phuongThucThanhToan;
    private String moTaThanhToan;
}
