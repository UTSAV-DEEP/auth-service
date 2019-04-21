package com.utsav.authservice.model.entities;

import com.utsav.authservice.common.Constants;
import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.dtos.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(unique=true)
    private String userName;

    private String displayName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id")
    private App app;

    @Column(unique=true)
    private String email;

    private String hashedPassword;

    private boolean admin;

    public static User from(UserDto userDto) {
        User user = Utils.MODEL_MAPPER.map(userDto, User.class);
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        user.setHashedPassword(hashedPassword);
        return user;
    }

}
