package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LopHocE {
    private int maLopHoc;
    private String tenLopHoc;
    private String moTaLopHoc;
    private float giaLopHoc;
    private Date lichHoc;
    private int soLuongThanhVienLopHoc;
}
