package com.document.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoles {
    Member("Member", "일반 사용자"),
    Master("Master", "관리자");

    private final String key;
    private final String title;
}
