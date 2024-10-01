package com.codecrafter.WebAppManagerRoomGymBE.security.api;

import com.codecrafter.WebAppManagerRoomGymBE.security.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.security.model.LoginRequest;
import com.codecrafter.WebAppManagerRoomGymBE.security.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        var requestBuilder = JwtIssuer.Request.builder()
                .userId(1L)
                .userName(request.getUserName())
                .roles(List.of("USER"))
                .build();
        var token = jwtIssuer.issue(requestBuilder);
            return LoginResponse.builder()
                    .accessToken(token)
                    .build();

    }
}
