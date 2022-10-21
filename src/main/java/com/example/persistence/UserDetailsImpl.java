package com.example.persistence;

import com.example.businesslayer.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String email;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public List<GrantedAuthority> getRolesAndAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
