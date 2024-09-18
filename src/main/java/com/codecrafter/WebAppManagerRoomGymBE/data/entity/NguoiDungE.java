package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

@Data
public class NguoiDungE {
   private int maNguoiDung;
    private String tenNguoiDung;
    private String matKhauNguoiDung;
    private boolean gioiTinhNguoiDung;
    private String soDienThoaiNguoiDung;
    private String moTaNguoiDung;
    private String anhNguoiDung;
    private int maVaiTro;
    private boolean hoatDongNguoiDung;
    private byte[] duLieuKhuonMatThanhVien;
}
