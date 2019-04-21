package com.utsav.authservice.services.impl;

import com.utsav.authservice.common.Constants;
import com.utsav.authservice.exceptions.HttpErrorException;
import com.utsav.authservice.model.dtos.LoginRespDto;
import com.utsav.authservice.model.dtos.LoginRqDto;
import com.utsav.authservice.model.entities.App;
import com.utsav.authservice.model.entities.User;
import com.utsav.authservice.repositories.AppRepository;
import com.utsav.authservice.repositories.UserRepository;
import com.utsav.authservice.services.CacheService;
import com.utsav.authservice.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    private final CacheService cacheService;

    private final AppRepository appRepository;

    private final UserRepository userRepository;

    public SessionServiceImpl(CacheService cacheService, AppRepository appRepository, UserRepository userRepository) {
        this.cacheService = cacheService;
        this.appRepository = appRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LoginRespDto login(LoginRqDto request, String appToken) {
        App app = appRepository.findAppByAppToken(appToken);
        if (null == app) {
            throw new HttpErrorException("Invalid app-token", HttpStatus.FORBIDDEN);
        }
        User user = userRepository.findUserByEmail(request.getEmail());
        if (null == user) {
            throw new HttpErrorException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        if (!BCrypt.checkpw(request.getPassword(), user.getHashedPassword())) {
            throw new HttpErrorException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        String randomUuid = UUID.randomUUID().toString();
        cacheService.set(randomUuid, String.valueOf(user.getId()));
        LOG.info("Login successful for email: {}", request.getEmail());
        return new LoginRespDto(randomUuid);
    }

    @Override
    public void logout(String authToken, String appToken) {
        App app = appRepository.findAppByAppToken(appToken);
        if (null == app) {
            throw new HttpErrorException("Invalid app-token", HttpStatus.FORBIDDEN);
        }
        String cachedTokenValue = cacheService.get(authToken);
        if (null == cachedTokenValue) {
            throw new HttpErrorException("Invalid Session", HttpStatus.UNAUTHORIZED);
        }
        LOG.info("Logging out user with auth-token: {}", authToken);
        cacheService.deleteKey(cachedTokenValue);
    }

    @Override
    public User getLoggedInUser(String authToken) {
        String idStr = cacheService.get(authToken);
        if (idStr == null) {
            throw new HttpErrorException("Invalid Session", HttpStatus.UNAUTHORIZED);
        }
        long userId = Long.valueOf(idStr);
        User loggedInUser = userRepository.findUserById(userId);
        LOG.info("Logged in user: {}", loggedInUser.toString());
        return loggedInUser;
    }

}


