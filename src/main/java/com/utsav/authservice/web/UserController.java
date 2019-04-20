package com.utsav.authservice.web;

import com.utsav.authservice.model.dtos.UserDto;
import com.utsav.authservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader(value = "app-token") String appToken,
                                                     @RequestHeader(value = "auth-token") String authToken) {
        return new ResponseEntity<>(userService.getAllUsers(appToken, authToken), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto,
                                                @RequestHeader(value = "app-token") String appToken) {
        return new ResponseEntity<>(userService.registerUser(userDto, appToken), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") long id,
                                                  @RequestHeader(value = "app-token") String appToken,
                                                  @RequestHeader(value = "auth-token") String authToken) {
        return new ResponseEntity<>(userService.getUserDetails(id, appToken, authToken), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @RequestHeader(value = "app-token") String appToken,
                                              @RequestHeader(value = "auth-token") String authToken) {
        return new ResponseEntity<>(userService.updateUser(userDto, appToken, authToken), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id, @RequestHeader(value = "app-token") String appToken
            , @RequestHeader(value = "auth-token") String authToken, @RequestParam(required = false, defaultValue =
            "false") boolean permanent) {
        userService.deleteUser(id, permanent, appToken, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
