package com.document.user.security;

import com.document.user.entity.UserRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    public static final String AUTH_HEADER = "Authorization";
    private final String PREFIX_BEARER = "Bearer ";
    private final int EXPIRE_TIME;
    private final SecretKey key;

    public JwtUtils(
            @Value("${jwt.access-expiration}") String time,
            @Value("${jwt.secret-key}") String secretKey
    ) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        // 확인
//        System.out.println(new String(bytes));
        key = Keys.hmacShaKeyFor(bytes);
        EXPIRE_TIME = Integer.parseInt(time);
    }

    // 토큰 생성
    public String generateToken(String username, UserRoles roles) {
        Date currentTime = new Date();

        return PREFIX_BEARER + Jwts.builder()
                .subject(username)
                .issuedAt(currentTime)
                .claim("Role", roles)
                .expiration(new Date(currentTime.getTime() + EXPIRE_TIME))
//                .signWith(key, SignatureAlgorithm.ES512)
//                .signWith(SIG.HS512.key().build(), SIG.HS512)
                .signWith(key)
                .compact();
    }

    // header에서 토큰 갖고 오기
    public String getJwtTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean checkValidJwtToken(String jwtToken) {
        try {
//            Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwtToken);
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
            return true;
        } catch(Exception e){
            log.error("에러 원인 : {} 에러 설명 : {}", e.getCause(), e.getMessage());
        }
        return false;
    }

    // 토큰에서 body 갖고 오기
    public Claims getBodyFromJwt(String jwtToken) {
//        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
    }

}
