package com.codecrafter.WebAppManagerRoomGymBE.data.model;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDungM {
    private int maNguoiDung;
    private String tenNguoiDung;
    @JsonIgnore
    private String matKhauNguoiDung;
    private boolean gioiTinhNguoiDung;
    private String soDienThoaiNguoiDung;
    private String moTaNguoiDung;
    private String anhNguoiDung;
    private int maVaiTro;
    private boolean hoatDongNguoiDung;
    private byte[] duLieuKhuonMatThanhVien;

public static NguoiDungM convertUserEToUserM(NguoiDungE nguoidungE) {
        return NguoiDungM.builder()
                .maNguoiDung(nguoidungE.getMaNguoiDung())
                .tenNguoiDung(nguoidungE.getTenNguoiDung())
                .matKhauNguoiDung(nguoidungE.getMatKhauNguoiDung())
                .gioiTinhNguoiDung(nguoidungE.isGioiTinhNguoiDung())
                .soDienThoaiNguoiDung(nguoidungE.getSoDienThoaiNguoiDung())
                .moTaNguoiDung(nguoidungE.getMoTaNguoiDung())
                .anhNguoiDung(nguoidungE.getAnhNguoiDung())
                .maVaiTro(nguoidungE.getMaVaiTro())
                .hoatDongNguoiDung(nguoidungE.isHoatDongNguoiDung())
                .duLieuKhuonMatThanhVien(nguoidungE.getDuLieuKhuonMatThanhVien())
                .build();
}
 public static List<NguoiDungM> convertListUserEToUserM(List<NguoiDungE> userEList) {
       return userEList.stream().map(userE -> convertUserEToUserM(userE)).collect(Collectors.toList());
    }

}

