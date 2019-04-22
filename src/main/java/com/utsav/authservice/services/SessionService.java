package com.utsav.authservice.services;

import com.utsav.authservice.model.dtos.LoginRespDto;
import com.utsav.authservice.model.dtos.LoginRqDto;
import com.utsav.authservice.model.dtos.UserDto;
import com.utsav.authservice.model.entities.User;

public interface SessionService {
    LoginRespDto login(LoginRqDto request, String appToken);

    void logout(String authToken, String appToken);

    User getLoggedInUser(String authToken);

    UserDto getLoggedInUser(String authToken, String appToken);

}
