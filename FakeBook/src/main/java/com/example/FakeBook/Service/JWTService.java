package com.example.FakeBook.Service;

import com.example.FakeBook.Entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JWTService {
    String takeToken();
    String generateToken(User user);
    SignedJWT verifyToken(String token) throws ParseException, JOSEException;
}
