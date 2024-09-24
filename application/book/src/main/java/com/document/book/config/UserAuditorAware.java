package com.document.book.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Configuration
public class UserAuditorAware implements AuditorAware<String> {

    private final HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        String userInfo = httpServletRequest.getHeader("userInfo");

        if (!StringUtils.hasText(userInfo)) {
            return Optional.empty();
        }

//        System.out.println("유저 정보 : " + userInfo);
        return Optional.of(userInfo);
    }
}
