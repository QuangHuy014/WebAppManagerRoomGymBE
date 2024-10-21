package com.codecrafter.WebAppManagerRoomGymBE.data.model;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhVienM {
    private int maThanhVien;
    private String tenThanhVien;
    private String emailThanhVien;
    @JsonIgnore
    private String matKhauNguoiDung;
    private long soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private String duLieuQrDinhDanh;

    @JsonBackReference // Ngăn không tuần tự hóa NguoiDungE khi tuần tự hóa ThanhVienE
    private NguoiDungM nguoiDung;

    private List<LichSuTapLuyenM> lichSuTapLuyen; // Giả định rằng bạn đã có một model tương ứng cho LichSuTapLuyenE

    public static ThanhVienM convertMemberEToMemberM(ThanhVienE thanhVienE) {
        return ThanhVienM.builder()
                .maThanhVien(thanhVienE.getMaThanhVien())
                .tenThanhVien(thanhVienE.getTenThanhVien())
                .emailThanhVien(thanhVienE.getEmailThanhVien())
                .matKhauNguoiDung(thanhVienE.getMatKhauNguoiDung())
                .soDienThoaiThanhVien(thanhVienE.getSoDienThoaiThanhVien())
                .ngaySinhThanhVien(thanhVienE.getNgaySinhThanhVien())
                .duLieuQrDinhDanh(thanhVienE.getDuLieuQrDinhDanh())
                .nguoiDung(NguoiDungM.convertUserEToUserM(thanhVienE.getNguoiDung())) // Chuyển đổi NguoiDungE sang NguoiDungM
                .build();
    }

    public static List<ThanhVienM> convertListMemberEToMemberM(List<ThanhVienE> thanhVienEList) {
        return thanhVienEList.stream()
                .map(thanhVienE -> convertMemberEToMemberM(thanhVienE))
                .collect(Collectors.toList());
    }
}
