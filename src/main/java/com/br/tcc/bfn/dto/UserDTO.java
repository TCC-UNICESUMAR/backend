package com.br.tcc.bfn.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDTO {

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDTO(String email, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
