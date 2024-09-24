package com.document.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Table(name = "_user")
@Entity
public class User {

    @Id
    @Column
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRoles userRoles;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //
    public static User RegisterUser(
            String username, String password, String firstName, String lastName,
            String phoneNumber, String email, String address) {

        User user = new User();
        user.username = username;
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        user.phoneNumber = phoneNumber;
        user.email = email;
        user.address = address;
        user.userRoles = UserRoles.Member;

        return user;
    }

    public void updateUserInfo(String address, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
