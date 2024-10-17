package com.codecrafter.WebAppManagerRoomGymBE.api;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponse;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/api-public/user")
public class NguoiDungAPI {
    @Autowired
    private NguoiDungService nguoiDungService;
    private final JwtIssuer jwtIssuer;

    public NguoiDungAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }
    @PostMapping("/login")
   public ResponseEntity<LoginResponse> login(@RequestBody NguoiDungDTO userDTO) {

    if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
        return ResponseEntity.status(400).body(LoginResponse.builder()
                .accessToken(null)
                .status(BasicApiConstant.FAILED.getStatus())
                .description("Tên người dùng không được để trống")
                .build());
    }
    if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
        return ResponseEntity.status(400).body(LoginResponse.builder()
                .accessToken(null)
                .status(BasicApiConstant.FAILED.getStatus())
                .description("Mật khẩu không được để trống")
                .build());
    }
        Optional<NguoiDungE> user = nguoiDungService.login(userDTO);
        if (user.isPresent()) {
            NguoiDungE nguoiDung = user.get();
            if (!nguoiDung.getMatKhauNguoiDung().equals(userDTO.getMatKhauNguoiDung())) {
                return ResponseEntity.status(401).body(LoginResponse.builder()
                        .accessToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }
            int maVaiTro = nguoiDung.getVaiTro().getMaVaiTro();
            String role = maVaiTro == 1 ? "ADMIN" : "MEMBER";
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) nguoiDung.getMaNguoiDung())
                    .username(nguoiDung.getTenNguoiDung())
                    .roles(List.of(role))
                    .build();
            var token = jwtIssuer.issue(requestBuilder);
            LoginResponse response = LoginResponse.builder()
                    .accessToken(token)
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }
    }
}






