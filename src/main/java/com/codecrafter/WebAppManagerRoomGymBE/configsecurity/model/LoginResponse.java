package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
     private String status;
    private String description;

}
