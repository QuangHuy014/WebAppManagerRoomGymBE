package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UuDaiE {
     private int maUuDai;
    private String moTaUuDai;
    private Date ngayBatDauUuDai;
    private Date ngayKetThucUuDai;
    private float giaTriUuDai;
    private boolean trangThaiUuDai;
}
