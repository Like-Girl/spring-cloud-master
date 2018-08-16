package cn.likegirl.rt.config.security.service;

import cn.likegirl.rt.config.security.model.AuthUserFactory;
import cn.likegirl.rt.mapper.UserMapper;
import cn.likegirl.rt.model.User;
import cn.likegirl.rt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(username);
        user = userService.get(user);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return AuthUserFactory.create(user);
        }
    }
}
