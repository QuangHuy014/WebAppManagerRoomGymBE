package com.codecrafter.WebAppManagerRoomGymBE.data.model;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserM {
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

public static UserM convertNguoiDungEToNguoiDungM(NguoiDungE nguoidungE) {
        return UserM.builder()
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
 public static List<UserM> convertListNguoiDungEToNguoiDungM(List<NguoiDungE> nguoiDungEList) {
        return nguoiDungEList.stream()
                .map(nguoiDungE -> convertNguoiDungEToNguoiDungM(nguoiDungE))
                .collect(Collectors.toList());
    }

}

