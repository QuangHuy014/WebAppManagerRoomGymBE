package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtDecoder;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtProperties;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.RegisterResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.TokenRefreshRequest;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.RegisterStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.VaiTroE;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class NguoiDungAPI {

    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private final JwtIssuer jwtIssuer;
    @Autowired
    private ThanhVienService thanhVienService;
    @Autowired
    private GoiTapService goiTapService;
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtDecoder jwtDecoder;

    public NguoiDungAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }


    @PostMapping("/register") // Endpoint cho đăng ký khách hàng
    public ResponseEntity<RegisterResponse> register(@RequestBody ThanhVienDTO thanhVienDTO, @RequestParam int maGoiTap) {
        // Kiểm tra xem tên khách hàng có rỗng không
        if (thanhVienDTO.getTenThanhVien() == null || thanhVienDTO.getTenThanhVien().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên khách hàng không được để trống.")
                    .build());
        }

        // Kiểm tra xem mật khẩu có rỗng không
        if (thanhVienDTO.getMatKhauNguoiDung() == null || thanhVienDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống.")
                    .build());
        }


    try {
        Optional<ThanhVienE> registeredMember = thanhVienService.register(thanhVienDTO, maGoiTap);

        // Nếu đăng ký thành công
        return ResponseEntity.ok(RegisterResponse.builder()
                .status(BasicApiConstant.SUCCEED.getStatus())
                .description(RegisterStatus.SUCCEED.getStateDescription())
                .build());
    } catch (IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(RegisterResponse.builder()
                .status(BasicApiConstant.FAILED.getStatus())
                .description(ex.getMessage())
                .build());
    }
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshAccessToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();

        try {
            // Giải mã refresh token
            DecodedJWT decodedJWT = jwtDecoder.decoded(refreshToken);
            String username = decodedJWT.getSubject();

            // Tạo access token mới từ thông tin giải mã refresh token
            var requestBuilder = JwtIssuer.Request.builder()
                    .username(username)
                    .username(String.valueOf(decodedJWT.getClaim("username").asLong()))
                    .roles(decodedJWT.getClaim("au").asList(String.class))
                    .build();

            String newAccessToken = jwtIssuer.issue(requestBuilder);

            // Trả về access token mới
            return ResponseEntity.ok(LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)  // Refresh token không đổi
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description("Access token mới được tạo thành công.")

                    .build());

        } catch (Exception e) {
            return ResponseEntity.status(401).body(LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Refresh token không hợp lệ hoặc đã hết hạn.")
                    .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody NguoiDungDTO userDTO) {
        if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên người dùng không được để trống")
                    .build());
        }
        if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống")
                    .build());
        }

        Optional<NguoiDungE> user = nguoiDungService.login(userDTO);
        if (user.isPresent()) {
            NguoiDungE nguoiDung = user.get();
            if (!passwordEncoder.matches(userDTO.getMatKhauNguoiDung(), nguoiDung.getMatKhauNguoiDung())) {
                return ResponseEntity.status(401).body(LoginResponse.builder()
                        .accessToken(null)
                        .refreshToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }


            VaiTroE vaiTro = nguoiDung.getVaiTro();
            String role = vaiTro != null ? vaiTro.getTenVaiTro() : null; // Kiểm tra nếu vai trò không null

            // Tạo request cho access token và refresh token
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) nguoiDung.getMaNguoiDung())
                    .username(nguoiDung.getTenNguoiDung())
                    .roles(List.of(role))
                    .build();

            // Tạo access token và refresh token
            var accessToken = jwtIssuer.issue(requestBuilder);
            var refreshToken = jwtIssuer.issueRefreshToken(requestBuilder); // Thêm refresh token

            LoginResponse response = LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tenNguoiDung(nguoiDung.getTenNguoiDung())
                    .role(role) // Thêm vai trò
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)  // Trả về refresh token null nếu không đăng nhập thành công
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }

    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<NguoiDungE> getUserInfo(@PathVariable int id) {
        Optional<NguoiDungE> user = nguoiDungService.getUserInfo(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body(null); // Hoặc trả về một response tùy chỉnh
        }
    }


}


