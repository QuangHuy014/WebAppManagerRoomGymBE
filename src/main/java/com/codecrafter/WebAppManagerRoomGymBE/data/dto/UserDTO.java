package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

@Data
public class UserDTO {
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
