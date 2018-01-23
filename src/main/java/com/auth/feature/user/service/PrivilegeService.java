package com.auth.feature.user.service;

import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.enums.PrivilegeEnum;

public interface PrivilegeService {

    Privilege savePrivilege(Privilege privilege);

    Privilege savePrivilegeAndFlush(Privilege privilege);

    Privilege getPrivilegeByType(PrivilegeEnum type);

    Privilege getPrivilegeById(Long id);
}
