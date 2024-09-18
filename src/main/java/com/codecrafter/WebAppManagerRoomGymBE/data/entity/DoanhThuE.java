package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DoanhThuE {
    private int maDoanhThu;
    private String loaiThoiGianDoanhThu;
    private int maThanhToan;
    private double soTienDoanhThu;  // Sử dụng double cho giá trị tiền
    private Date ngayTaoDoanhThu;
}
