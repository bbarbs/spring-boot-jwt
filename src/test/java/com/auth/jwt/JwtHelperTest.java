package com.auth.jwt;

import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.SignatureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class JwtHelperTest {

    private String USER_EMAIL = "TestUser@gmail.com";

    @InjectMocks
    JwtHelper jwtHelper;

    @InjectMocks
    JwtUtil jwtUtil;

    @Test
    public void testShouldGenerateAccessToken() throws Exception {
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, this.jwtUtil.generateKey());
        assertNotNull(model);
        assertThat(USER_EMAIL).isEqualTo(model.getSubject());
    }

    @Test
    public void testShouldValidateToken() throws Exception {
        SecretKey secretKey = this.jwtUtil.generateKey();
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, secretKey);
        assertNotNull(secretKey);
        assertNotNull(model);
        Claims claims = this.jwtHelper.validateToken(secretKey, model);
        assertNotNull(claims);
        assertThat(claims.getSubject()).isEqualTo(model.getSubject());
    }

    @Test(expected = SignatureException.class)
    public void testShouldFailValidateTokenWithInvalidSecretKey() throws NoSuchAlgorithmException {
        SecretKey dummyKey = this.jwtUtil.generateKey();
        SecretKey secretKey = this.jwtUtil.generateKey();
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, secretKey);
        assertNotNull(dummyKey);
        assertNotNull(model);
        this.jwtHelper.validateToken(dummyKey, model);
    }

    @Test(expected = IncorrectClaimException.class)
    public void testShouldFailValidateTokenWithInvalidClaims() throws NoSuchAlgorithmException {
        SecretKey secretKey = this.jwtUtil.generateKey();
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, secretKey);
        model.setSubject("Test");
        assertNotNull(secretKey);
        assertNotNull(model);
        this.jwtHelper.validateToken(secretKey, model);
    }
}
