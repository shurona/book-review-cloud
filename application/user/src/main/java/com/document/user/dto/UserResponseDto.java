package com.document.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private String username;
    private String email;
    private String phoneNumber;
    private String address;

    public UserResponseDto(String username, String email, String phoneNumber, String address) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
