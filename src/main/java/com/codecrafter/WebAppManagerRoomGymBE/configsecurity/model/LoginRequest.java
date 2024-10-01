package com.codecrafter.WebAppManagerRoomGymBE.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LoginRequest {

    private String userName;
    private String passWord;
}
