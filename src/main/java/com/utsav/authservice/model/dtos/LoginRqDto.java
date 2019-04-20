package com.utsav.authservice.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginRqDto implements Serializable {

    private String email;

    private String password;
}
