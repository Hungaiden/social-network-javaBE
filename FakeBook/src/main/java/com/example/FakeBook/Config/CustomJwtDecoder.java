package com.example.FakeBook.Config;

import com.example.FakeBook.Repository.InvalidatedTokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${signer_key}")
    private String SIGNER_KEY;

    @Override
    public Jwt decode(String token) throws JwtException {
        try{
            //Xac thuc
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);

            if (!(verified && expiration.after(new Date()))
                    || (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID().toString()))) {
                throw new JwtException("Invalid token");
            }
        } catch (ParseException |  JOSEException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
