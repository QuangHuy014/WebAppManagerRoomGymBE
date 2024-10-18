package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gym_thanh_vien")
public class ThanhVienE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maThanhVien;

    private String tenThanhVien;
    private String emailThanhVien;
    private String matKhauNguoiDung;
    private long soDienThoaiThanhVien;
    private Date ngaySinhThanhVien;
    private String duLieuQrDinhDanh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ma_nguoi_dung")
    @JsonBackReference // Ngăn không tuần tự hóa NguoiDungE khi tuần tự hóa ThanhVienE
    private NguoiDungE nguoiDung;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "thanhVien", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<LichSuTapLuyenE> lichSuTapLuyen;
}
