package com.auth.security;

import com.auth.core.jwt.JwtHelper;
import com.auth.core.jwt.JwtModel;
import com.auth.core.jwt.util.JwtConstant;
import com.auth.core.jwt.util.JwtUtil;
import com.auth.feature.token.service.impl.TokenServiceImpl;
import com.auth.feature.user.model.enums.RoleEnum;
import com.auth.feature.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {

    private String USER_EMAIL = "test@gmail.com";
    private String USER_PASS = "pass";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    TokenServiceImpl tokenService;

    @InjectMocks
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    JwtHelper jwtHelper;

    @InjectMocks
    JwtUtil jwtUtil;

    @Test
    public void testShouldAccessAuthorizeRequest() throws Exception {
        // Generate token.
        JwtModel model = this.jwtHelper.generateAccessToken(USER_EMAIL, this.jwtUtil.generateKey());
        // User details and encoded password.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name()));
        User user = new User(USER_EMAIL, this.passwordEncoder.encode(USER_PASS), authorities);
        // Stub user details.
        when(this.userService.loadUserByUsername(USER_EMAIL)).thenReturn(user);
        // Stub token details.
        when(this.tokenService.getSecretKey(model.getToken())).thenReturn(model);
        // Mock access to user controller getAllUser with preauthorize role.
        this.mockMvc
                .perform(get("/users")
                        .accept(APPLICATION_JSON)
                        .header(JwtConstant.AUTHORIZATION_HEADER_STRING, JwtConstant.TOKEN_BEARER_PREFIX + model.getToken())
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
