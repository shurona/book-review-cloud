package com.document.user.service;

import com.document.user.dto.RegisterUserRequestDto;
import com.document.user.dto.UpdateUserRequestDto;
import com.document.user.dto.UserResponseDto;
import com.document.user.entity.User;
import com.document.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @Transactional
    public String registerUser(RegisterUserRequestDto requestDto) {

        userRepository.findById(requestDto.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("이미 있는 Username 입니다.");
        });

        User user = User.RegisterUser(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getPhoneNumber(),
                requestDto.getEmail(),
                requestDto.getAddress()
        );

        userRepository.save(user);

        return user.getUsername();
    }

    public UserResponseDto getUserById(String userId) {
        User userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));


        return new UserResponseDto(userInfo.getUsername(), userInfo.getEmail(), userInfo.getPhoneNumber(), userInfo.getAddress());
    }

    @Transactional
    public UserResponseDto updateUserById(String userId, UpdateUserRequestDto requestDto) {

        User userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        userInfo.updateUserInfo(requestDto.getAddress(), requestDto.getPhoneNumber());

        return new UserResponseDto(userInfo.getUsername(), userInfo.getEmail(), userInfo.getPhoneNumber(), userInfo.getAddress());
    }

}
