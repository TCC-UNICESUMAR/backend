package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOPopulator implements Populator<UserDTO, User>{
    @Override
    public void populate(UserDTO userDTO, User user) {
        userDTO.setEmail(user.getEmail());
        userDTO.setProfileImage(user.getProfileImage());
        userDTO.setName(user.getName());
    }
}
