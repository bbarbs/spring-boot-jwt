package com.auth.logout;

import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtConstant;
import com.auth.core.jwt.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogoutTest {

    private String USER_EMAIL = "test@gmail.com";

    @Inject
    MockMvc mockMvc;

    @InjectMocks
    JwtHelper jwtHelper;

    @InjectMocks
    JwtUtil jwtUtil;

    @Test
    public void testShouldLogoutUser() throws Exception {
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, this.jwtUtil.generateKey());
        this.mockMvc
                .perform(get("/logout")
                        .header(JwtConstant.AUTHORIZATION_HEADER_STRING, JwtConstant.TOKEN_BEARER_PREFIX + model.getToken())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
