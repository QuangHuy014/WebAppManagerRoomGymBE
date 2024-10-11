package com.codecrafter.WebAppManagerRoomGymBE.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="gym_lop_hoc")
public class LopHocE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maLopHoc;
    private String tenLopHoc;
    private String moTaLopHoc;
    private float giaLopHoc;
    private Date lichHoc;
    private int soLuongThanhVienLopHoc;
}
