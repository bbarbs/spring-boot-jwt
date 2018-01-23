package com.auth.web;

import com.auth.core.rest.patch.Patch;
import com.auth.core.rest.patch.PatchEnum;
import com.auth.feature.token.service.impl.TokenServiceImpl;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.UserDto;
import com.auth.feature.user.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    MockMvc mockMvc;

    @Inject
    WebApplicationContext context;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    TokenServiceImpl tokenService;

    @Before
    public void setup() {
        // Integrate security filter chain to test security features of spring.
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testShouldGetAllUser() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testShouldFailGetAllUserWithNoAuthorities() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"READ"})
    public void testShouldGetUserById() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("pass");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEnabled(true);
        when(this.userService.getUserById(1L)).thenReturn(user);
        this.mockMvc
                .perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "WRITE"})
    public void testShouldDeleteUserById() throws Exception {
        this.mockMvc
                .perform(delete("/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "WRITE"})
    public void testShouldPatchUserEmail() throws Exception {
        Patch patch = new Patch();
        patch.setField("email");
        patch.setPatchEnum(PatchEnum.REPLACE);
        patch.setValue("new@gmail.com");
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(patch);
        when(this.userService.patchUser(1L, patch)).thenReturn(user);
        this.mockMvc
                .perform(patch("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content(body)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "WRITE"})
    public void testShouldUpdateUserById() throws Exception {
        UserDto dto = new UserDto();
        dto.setEmail("test@gmail.com");
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setPassword("pass");
        dto.setEnabled(true);
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(dto);
        when(this.userService.updateUser(1L, dto)).thenReturn(user);
        this.mockMvc
                .perform(put("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content(body)
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }
}
