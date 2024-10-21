package com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security;

import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final NguoiDungService nguoiDungService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = nguoiDungService.findByUserName(username);
        return UserPrincipal.builder()
                .userId((long) user.get().getMaNguoiDung())
                .userName(user.get().getTenNguoiDung())
                .passWord(user.get().getMatKhauNguoiDung())
                .authorities(List.of(new SimpleGrantedAuthority(user.get().getVaiTro().getTenVaiTro())))
                .build();
    }
}
