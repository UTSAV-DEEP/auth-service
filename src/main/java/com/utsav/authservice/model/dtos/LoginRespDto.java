package com.utsav.authservice.model.dtos;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespDto {
    private String authToken;
}
