package com.utsav.authservice.model.entities;

import com.utsav.authservice.common.Constants;
import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.dtos.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table
@Entity(name = "users")
public class User extends AbstractEntity {

    @Column(unique=true)
    private String userName;

    private String displayName;

    private App app;

    @Column(unique=true)
    private String email;

    private String hashedPassword;

    private boolean admin;

    public static User from(UserDto userDto) {
        User user = Utils.MODEL_MAPPER.map(userDto, User.class);
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), Constants.PW_SALT);
        user.setHashedPassword(hashedPassword);
        return user;
    }

}
