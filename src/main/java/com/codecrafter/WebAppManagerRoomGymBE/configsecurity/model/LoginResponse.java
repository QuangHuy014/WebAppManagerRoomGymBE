package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
     private String status; // Trạng thái của phản hồi
    private String description; // Mô tả thêm về phản hồi

}
