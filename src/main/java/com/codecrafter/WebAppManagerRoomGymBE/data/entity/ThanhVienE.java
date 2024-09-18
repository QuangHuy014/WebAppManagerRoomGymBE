package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ThanhVienE {
    private int maThanhVien;
    private String tenThanhVien;
    private String emailThanhVien;
    private String soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private byte[] duLieuKhuonMatThanhVien;
    private int maNguoiDung;
}
