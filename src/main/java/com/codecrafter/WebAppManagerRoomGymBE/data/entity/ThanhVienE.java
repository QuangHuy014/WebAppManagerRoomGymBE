package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ThanhVienE {
    private int maThanhVien;
    private String tenThanhVien;
    private String emailThanhVien;
    private String matKhauNguoiDung;
    private String soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private String duLieuQrDinhDanh;
    private int maNguoiDung;
}
