package com.auth.security;

import com.auth.feature.token.service.impl.TokenServiceImpl;
import com.auth.feature.user.model.dto.AuthRequest;
import com.auth.feature.user.model.enums.RoleEnum;
import com.auth.feature.user.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    private String USER_EMAIL = "test@gmail.com";
    private String USER_PASS = "pass";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @InjectMocks
    BCryptPasswordEncoder passwordEncoder;

    @MockBean
    TokenServiceImpl tokenService;

    /**
     * Test user must be able to successfully login.
     *
     * @throws Exception
     */
    @Test
    public void testShouldLogInUser() throws Exception {
        // User credentials.
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(new AuthRequest(USER_EMAIL, USER_PASS));
        // User details and encoded password.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_USER.name()));
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name()));
        User user = new User(USER_EMAIL, this.passwordEncoder.encode(USER_PASS), authorities);
        // Stub user details.
        given(this.userService.loadUserByUsername(USER_EMAIL)).willReturn(user);
        this.mockMvc
                .perform(post("/login")
                        .contentType(APPLICATION_JSON)
                        .content(payload)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test must fail when login since password in created {@link User} is not encoded.
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void testShouldFailLogInUserWithBadCredentials() throws Exception {
        // User credentials.
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(new AuthRequest(USER_EMAIL, USER_PASS));
        // User details and password is not encoded.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_USER.name()));
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_ADMIN.name()));
        User user = new User(USER_EMAIL, USER_PASS, authorities);
        // Stub user details.
        given(this.userService.loadUserByUsername(USER_EMAIL)).willReturn(user);
        this.mockMvc
                .perform(post("/login")
                        .contentType(APPLICATION_JSON)
                        .content(payload)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
