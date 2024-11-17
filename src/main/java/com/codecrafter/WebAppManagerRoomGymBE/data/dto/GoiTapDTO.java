package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoiTapDTO {
    private int maGoiTap;
    private String tenGoiTap;
    private String moTaGoiTap;
    private float giaGoiTap;
    private Date thoiHanGoiTap;
}
