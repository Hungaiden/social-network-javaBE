package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.AuthenticationRequest;
import com.example.FakeBook.DTO.Response.AuthenticationResponse;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.JWTService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class AuthFacadeService {
    @Autowired
    private AuthService authService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailResponse myInfo() throws ParseException, JOSEException {
        SignedJWT signedJWT = jwtService.verifyToken(jwtService.takeToken());
        return authService.myInfo(signedJWT.getJWTClaimsSet().getSubject());
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        String token = jwtService.generateToken(authService.login(request.getUsername(), request.getPassword()));
        log.info("Tao token thanh cong");
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout() throws ParseException, JOSEException {
        SignedJWT signedJWT = jwtService.verifyToken(jwtService.takeToken());
        String id = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        authService.logout(id, expiryTime);
        log.warn("Logout thanh cong!");
    }
}
