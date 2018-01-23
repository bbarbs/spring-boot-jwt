package com.auth.feature.user.model.dto;

import com.auth.feature.user.model.enums.PrivilegeEnum;
import com.auth.feature.user.model.enums.RoleEnum;

import java.util.List;

public class RoleDto {

    private RoleEnum roleEnum;
    private List<PrivilegeEnum> privilegeEnums;

    public RoleDto() {
    }

    public RoleDto(RoleEnum roleEnum, List<PrivilegeEnum> privilegeEnums) {
        this.roleEnum = roleEnum;
        this.privilegeEnums = privilegeEnums;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public List<PrivilegeEnum> getPrivilegeEnums() {
        return privilegeEnums;
    }

    public void setPrivilegeEnums(List<PrivilegeEnum> privilegeEnums) {
        this.privilegeEnums = privilegeEnums;
    }
}
