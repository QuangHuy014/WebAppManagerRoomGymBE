package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DoanhThuDTO {
    private int maDoanhThu;
    private String loaiThoiGianDoanhThu;
    private double soTienThanhToan;
    private Date ngayTaoDoanhThu;
}
