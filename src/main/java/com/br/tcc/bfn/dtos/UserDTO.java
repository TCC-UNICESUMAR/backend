package com.br.tcc.bfn.dtos;

import com.br.tcc.bfn.models.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDTO {

    private Long id;
    private String email;
    private String profileImageId;

    public UserDTO() {
    }

    public UserDTO(User user) {
        id = user.getUserId();
        email = user.getEmail();
        profileImageId = user.getProfileImageId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }
}
