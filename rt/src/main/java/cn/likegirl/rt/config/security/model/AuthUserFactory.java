package cn.likegirl.rt.config.security.model;

import cn.likegirl.rt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUserFactory {

    private AuthUserFactory(){}

    public static AuthUser create(User user) {
        List<String> roles = new ArrayList<String>(){{
            add("SYSTEM");
        }};
        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(roles)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
