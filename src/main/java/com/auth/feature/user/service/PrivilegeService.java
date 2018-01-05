package com.auth.feature.user.service;

import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.enums.PrivilegeType;

public interface PrivilegeService {

    Privilege savePrivilege(Privilege privilege);

    Privilege savePrivilegeAndFlush(Privilege privilege);

    Privilege getPrivilegeByType(PrivilegeType type);

    Privilege getPrivilegeById(Long id);
}
