package com.godana.domain.dto.user;

import com.godana.domain.entity.Role;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdminRegisterReqDTO {
    private String username;
    private String password;
    private Long roleId;

    public User toUser(Role role){
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                ;
    }
}
