package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ThanhVienDTO {
    private int maThanhVien;
    private String tenThanhVien;
    private String matKhauNguoiDung;
    private String emailThanhVien;
    private long soDienThoaiThanhVien;
    private LocalDate ngaySinhThanhVien;
    private String duLieuQrDinhDanh;


}
