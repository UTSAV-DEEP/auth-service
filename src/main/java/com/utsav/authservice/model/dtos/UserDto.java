package com.utsav.authservice.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.utsav.authservice.common.Utils;
import com.utsav.authservice.model.entities.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties
public class UserDto {

    @NotNull
    private Long id;

    private String userName;

    private String displayName;

    private AppDto appDto;

    private String email;

    private String password;

    private boolean admin;

    private long createdAt;

    private long updatedAt;

    public static UserDto from(User userEntity) {
        UserDto userDto = Utils.MODEL_MAPPER.map(userEntity, UserDto.class);
        userDto.setCreatedAt(userEntity.getCreatedAt().getTime());
        userDto.setUpdatedAt(userEntity.getUpdatedAt().getTime());
        userDto.setPassword(null);
        return userDto;
    }

}
