package com.br.tcc.bfn.dtos;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDTO {

    private Long id;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDTO(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
