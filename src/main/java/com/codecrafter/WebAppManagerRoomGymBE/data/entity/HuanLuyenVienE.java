package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HuanLuyenVienE {
    private int maHuanLuyenVien;
    private String tenHuanLuyenVien;
    private String moTaLopHoc;
    private String emailHuanLuyenVien;
    private String soDienThoaiHuanLuyenVien;
    private String chuyenMonHuanLuyenVien;
    private Date lichLamViecHuanLuyenVien;
    private int maLopHoc;
    private String duLieuQrDinhDanh;
}
