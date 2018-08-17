package cn.likegirl.rt.config.security.provider;

import cn.likegirl.rt.config.security.model.AuthUser;
import cn.likegirl.rt.config.security.model.AuthUserFactory;
import cn.likegirl.rt.config.security.service.PasswordEncoderService;
import cn.likegirl.rt.model.User;
import cn.likegirl.rt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        User user = new User();
        user.setUsername(username);
        user = userService.get(user);

        if (user == null) {
            throw new BadCredentialsException("security.auth.exception.wrong_principal");
        }

//        if (!passwordEncoderService.isPasswordValid(user.getPassword(),password ,user.getSalt() )) {
//            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//        }

        AuthUser authUser = AuthUserFactory.create(user);

        return new UsernamePasswordAuthenticationToken(authUser, password, authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
