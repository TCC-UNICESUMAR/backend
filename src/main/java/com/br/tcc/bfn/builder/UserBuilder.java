package com.br.tcc.bfn.builder;

import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.models.User;

import java.util.Date;
import java.util.List;

public class UserBuilder {

    private User user;

    private  UserBuilder(){
        user = new User();
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public UserBuilder name(String name){
        this.user.setName(name);
        return this;
    }

    public UserBuilder cpfOrCnpj(String cpfOrCnpj){
        this.user.setCpfOrCnpj(cpfOrCnpj);
        return this;
    }
    public UserBuilder email(String email){
        this.user.setEmail(email);
        return this;
    }
    public UserBuilder password(String password){
        this.user.setPassword(password);
        return this;
    }
    public UserBuilder active(Boolean active){
        this.user.setActive(active);
        return this;
    }
    public UserBuilder updateAt(Date updateAt){
        this.user.setUpdateAt(updateAt);
        return this;
    }

    public UserBuilder createdAt(Date createdAt){
        this.user.setCreatedAt(createdAt);
        return this;
    }
    public UserBuilder profileImageId(String profileImageId){
        this.user.setProfileImageId(profileImageId);
        return this;
    }

    public UserBuilder phone(String phone){
        this.user.setPhone(phone);
        return this;
    }
    public UserBuilder roles(List<Role> roles){
        this.user.setRoles(roles);
        return this;
    }

    public UserBuilder address(Address address){
        this.user.setAddress(address);
        return this;
    }

    public User build(){
        return this.user;
    }
}
