package com.codecrafter.WebAppManagerRoomGymBE.data.dto;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.VaiTroE;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class NguoiDungDTO {
    private int maNguoiDung;
    private String tenNguoiDung;
    private String matKhauNguoiDung;
    private boolean gioiTinhNguoiDung;
    private String soDienThoaiNguoiDung;
    private String moTaNguoiDung;
    private String anhNguoiDung;
    private VaiTroE vaiTro;
    private boolean hoatDongNguoiDung;
    private String duLieuQrDinhDanh;
}
