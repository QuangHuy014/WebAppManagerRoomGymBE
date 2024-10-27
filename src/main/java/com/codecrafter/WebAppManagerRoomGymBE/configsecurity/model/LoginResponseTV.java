package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseTV {
      private final String accessToken;
      private int maLichSuTapLuyen;
    private String tenThanhVien;
     private String status;
    private String description;

}
