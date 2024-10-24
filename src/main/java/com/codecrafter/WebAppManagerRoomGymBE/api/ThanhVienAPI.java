package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponseTV;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
public class ThanhVienAPI {
      @Autowired
    private ThanhVienService thanhVienService;

    @Autowired
    private final JwtIssuer jwtIssuer;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ThanhVienAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }


  @PostMapping("/login")
    public ResponseEntity<LoginResponseTV> login(@RequestBody ThanhVienDTO memberDTO) {
        // Kiểm tra tên thành viên
        if (memberDTO.getTenThanhVien() == null || memberDTO.getTenThanhVien().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên thành viên không được để trống")
                    .build());
        }
        // Kiểm tra mật khẩu
        if (memberDTO.getMatKhauNguoiDung() == null || memberDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống")
                    .build());
        }

        // Gọi dịch vụ để xác thực đăng nhập
        Optional<ThanhVienE> member = thanhVienService.login(memberDTO);
        if (member.isPresent()) {
            ThanhVienE thanhVien = member.get();

            if (!passwordEncoder.matches(memberDTO.getMatKhauNguoiDung(), thanhVien.getMatKhauNguoiDung())) {
            return ResponseEntity.status(401).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                    .build());
        }



            // Tạo yêu cầu để phát hành JWT
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) thanhVien.getMaThanhVien()) // ID thành viên
                    .username(thanhVien.getTenThanhVien()) // Tên thành viên
                    .build();

            // Phát hành token
            var token = jwtIssuer.issue(requestBuilder);

            // Tạo phản hồi LoginResponseTV với tên thành viên
            LoginResponseTV response = LoginResponseTV.builder()
                    .accessToken(token)
                    .tenThanhVien(thanhVien.getTenThanhVien()) // Thêm tên thành viên
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription()) // Thêm thông điệp thành công
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(LoginResponseTV.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }
    }

}
