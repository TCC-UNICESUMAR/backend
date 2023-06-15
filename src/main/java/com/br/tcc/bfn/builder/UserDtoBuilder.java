package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;

import java.util.Date;
import java.util.List;

public class UserDtoBuilder {

    private UserDTO user;

    private UserDtoBuilder(){
        user = new UserDTO();
    }

    public static UserDtoBuilder builder(){
        return new UserDtoBuilder();
    }

    public UserDtoBuilder name(String name){
        this.user.setName(name);
        return this;
    }

    public UserDtoBuilder email(String email){
        this.user.setEmail(email);
        return this;
    }
    public UserDtoBuilder profileImageId(String profileImageId){
        this.user.setProfileImageId(profileImageId);
        return this;
    }
    public UserDtoBuilder roles(List<Role> roles){
        this.user.setRoles(roles);
        return this;
    }
    
    public UserDTO build(){
        return this.user;
    }
}
