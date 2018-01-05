package com.auth.feature.user.service;

import com.auth.core.rest.patch.Patch;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserInfo saveUser(UserInfo user);

    UserInfo saveUserAndFlush(UserInfo user);

    UserInfo updateUser(Long id, UserDto dto);

    void removeUserById(Long id);

    List<UserInfo> getAllUser();

    UserInfo getUserByEmail(String email);

    UserInfo getUserById(Long id);

    UserInfo patchUser(Long id, Patch patch);

    boolean isEmailAlreadyExist(String email);
}
