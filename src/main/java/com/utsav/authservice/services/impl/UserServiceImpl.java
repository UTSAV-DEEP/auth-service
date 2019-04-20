package com.utsav.authservice.services.impl;

import com.utsav.authservice.common.Utils;
import com.utsav.authservice.exceptions.HttpErrorException;
import com.utsav.authservice.model.dtos.AppDto;
import com.utsav.authservice.model.dtos.UserDto;
import com.utsav.authservice.model.entities.App;
import com.utsav.authservice.model.entities.User;
import com.utsav.authservice.repositories.AppRepository;
import com.utsav.authservice.repositories.UserRepository;
import com.utsav.authservice.services.SessionService;
import com.utsav.authservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AppRepository appRepository;

    private final SessionService sessionService;

    public UserServiceImpl(UserRepository userRepository, AppRepository appRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.appRepository = appRepository;
        this.sessionService = sessionService;
    }

    @Override
    public UserDto registerUser(UserDto userDto, String appToken) {
        App app = appRepository.findAppByAppToken(appToken);
        if (null == app) {
            throw new HttpErrorException(String.format("Invalid appToken: %s", appToken), HttpStatus.UNAUTHORIZED);
        }
        User user = User.from(userDto);
        user.setApp(app);
        user = userRepository.save(user);
        UserDto registeredUser = UserDto.from(user);
        registeredUser.setAppDto(AppDto.from(app));
        return registeredUser;
    }

    @Override
    public List<UserDto> getAllUsers(String appToken, String authToken) {
        User user = sessionService.getLoggedInUser(authToken);
        App app = appRepository.findAppByAppToken(appToken);
        if (null != app) {
            return Utils.toList(userRepository.findAllUsersOfApp(app.getName()), UserDto::from);
        } else if (user.isAdmin()) {
            return Utils.toList(userRepository.findAllUsers(), UserDto::from);
        } else {
            throw new HttpErrorException(String.format("Invalid appToken: %s", appToken), HttpStatus.UNAUTHORIZED);
        }

    }

    private User getValidatedUser(long id, String appToken, User user) {
        User requestedUser = userRepository.findUserById(id);
        if (user.isAdmin()) {
            if (null == requestedUser) {
                throw new HttpErrorException(String.format("User with id: %s not found", id), HttpStatus.NOT_FOUND);
            }
            return requestedUser;
        }
        App app = appRepository.findAppByAppToken(appToken);
        if (null == app) {
            throw new HttpErrorException(String.format("Invalid appToken: %s", appToken), HttpStatus.FORBIDDEN);
        }
        if (app.getId() != requestedUser.getApp().getId()) {
            throw new HttpErrorException(String.format("User with id: %s not found", id), HttpStatus.NOT_FOUND);
        }
        return requestedUser;
    }

    @Override
    public UserDto getUserDetails(long id, String appToken, String authToken) {
        User user = sessionService.getLoggedInUser(authToken);
        return UserDto.from(getValidatedUser(id, appToken, user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, String appToken, String authToken) {
        User user = sessionService.getLoggedInUser(authToken);
        User requestedUser = getValidatedUser(userDto.getId(), appToken, user);
        if(StringUtils.isNotBlank(userDto.getDisplayName())) {
            requestedUser.setDisplayName(userDto.getDisplayName());
        }
        if(StringUtils.isNotBlank(userDto.getEmail())) {
            requestedUser.setEmail(userDto.getEmail());
        }
        if(StringUtils.isNotBlank(userDto.getUserName())) {
            requestedUser.setUserName(userDto.getUserName());
        }
        requestedUser = userRepository.save(requestedUser);
        return UserDto.from(requestedUser);
    }

    @Override
    public void deleteUser(long id, boolean permanent, String appToken, String authToken) {
        User user = sessionService.getLoggedInUser(authToken);
        User requestedUser = getValidatedUser(id, appToken, user);
        if(user.isAdmin() || requestedUser.getId() == user.getId()) {
            if(permanent) {
                userRepository.delete(requestedUser);
            }
            else {
                requestedUser.setDeleted(true);
                requestedUser.setDeletedAt(new Date());
            }
        }
        else {
            throw new HttpErrorException("You are not authorized to delete the user with id " + id, HttpStatus.FORBIDDEN);
        }
    }
}
