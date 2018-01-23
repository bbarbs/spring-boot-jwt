package com.auth.service;

import com.auth.core.rest.patch.Patch;
import com.auth.core.rest.patch.PatchEnum;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.UserDto;
import com.auth.feature.user.repository.UserRepository;
import com.auth.feature.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void testShouldSaveUser() {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        when(this.userRepository.save(user)).thenReturn(user);
        UserInfo result = this.userService.saveUser(user);
        assertThat(user.getEmail()).isEqualTo(result.getEmail());
    }

    @Test
    public void testShouldUpdateUser() {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        given(this.userRepository.findOne(1L)).willReturn(user);
        UserDto dto = new UserDto();
        dto.setEmail("new@gmail.com");
        dto.setFirstName("new");
        // Update user.
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        when(this.userRepository.save(user)).thenReturn(user);
        UserInfo result = this.userService.updateUser(1L, dto);
        assertThat(dto.getEmail()).isEqualTo(result.getEmail());
        assertThat(dto.getFirstName()).isEqualTo(result.getFirstName());
    }

    @Test
    public void testShouldRemoveUserById() {
        given(this.userRepository.findOne(0L)).willReturn(new UserInfo());
        this.userService.removeUserById(0L);
        verify(this.userRepository, times(1)).delete(0L);
    }

    @Test
    public void testShouldGetAllUser() {
        List<UserInfo> list = new ArrayList<>();
        UserInfo user1 = new UserInfo();
        user1.setEmail("user1@gmail.com");
        UserInfo user2 = new UserInfo();
        user2.setEmail("user2@gmail.com");
        list.add(user1);
        list.add(user2);
        when(this.userRepository.findAll()).thenReturn(list);
        List<UserInfo> results = this.userService.getAllUser();
        assertEquals(2, results.size());
    }

    @Test
    public void testShouldGetUserByEmail() {
        UserInfo user = new UserInfo();
        user.setEmail("user1@gmail.com");
        when(this.userRepository.findByEmail(Matchers.anyObject())).thenReturn(user);
        UserInfo result = this.userService.getUserByEmail(user.getEmail());
        assertThat("user1@gmail.com").isEqualTo(result.getEmail());
    }

    @Test
    public void testShouldPatchUserEmail() {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        given(this.userRepository.findOne(Matchers.anyLong())).willReturn(user);
        Patch patch = new Patch();
        patch.setValue("new@gmail.com");
        patch.setPatchEnum(PatchEnum.REPLACE);
        patch.setField("email");
        // Update email.
        user.setEmail(patch.getValue());
        when(this.userRepository.saveAndFlush(user)).thenReturn(user);
        UserInfo result = this.userService.patchUser(Matchers.anyLong(), patch);
        assertThat(patch.getValue()).isEqualTo(result.getEmail());
    }
}
