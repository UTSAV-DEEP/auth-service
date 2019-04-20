package com.utsav.authservice.services;

import com.utsav.authservice.model.dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto, String appToken);

    List<UserDto> getAllUsers(String appToken, String authToken);

    UserDto getUserDetails(long id, String appToken, String authToken);

    UserDto updateUser(UserDto userDto, String appToken, String authToken);

    void deleteUser(long id, boolean permanent, String appToken, String authToken);

}
