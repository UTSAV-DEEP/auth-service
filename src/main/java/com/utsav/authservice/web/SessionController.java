package com.utsav.authservice.web;

import com.utsav.authservice.model.dtos.LoginRespDto;
import com.utsav.authservice.model.dtos.LoginRqDto;
import com.utsav.authservice.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {


    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<LoginRespDto> login(@RequestBody LoginRqDto loginRqDto,
                                              @RequestHeader(value = "app-token") String appToken) {
        return new ResponseEntity<>(sessionService.login(loginRqDto, appToken), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity logout(@RequestHeader(value = "app-token") String appToken
            , @RequestHeader(value = "auth-token") String authToken) {
        sessionService.logout(authToken, appToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
