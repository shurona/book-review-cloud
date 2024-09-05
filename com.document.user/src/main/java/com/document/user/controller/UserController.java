package com.document.user.controller;

import com.document.user.dto.RegisterUserRequestDto;
import com.document.user.dto.UpdateUserRequestDto;
import com.document.user.dto.UserResponseDto;
import com.document.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUsersById(
            @PathVariable("id") String username
    ) {
        UserResponseDto userInfo = userService.getUserById(username);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequestDto requestDto) {
        String userId = userService.registerUser(requestDto);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUsersById(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateUserRequestDto requestDto
    ) {
        UserResponseDto userResponseDto = userService.updateUserById(userDetails.getUsername(), requestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsersById(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: 삭제는 추후 구현

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
