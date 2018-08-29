package cn.likegirl.rt.config.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.io.ByteSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class AuthUser implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String salt;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(
            Long id,
            String username,
            String password,
            String salt,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    public String getCredentialsSalt() {
        return  username + salt;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * 账户是否未过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *  账户是否激活
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
