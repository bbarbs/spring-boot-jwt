package com.auth.feature.user.service;

import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.enums.RoleEnum;

public interface RoleService {

    Role saveRole(Role role);

    Role saveRoleAndFlush(Role role);

    Role getRoleByType(RoleEnum type);

    Role getRoleById(Long id);
}
