package com.auth.setup;

import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.enums.PrivilegeType;
import com.auth.feature.user.model.enums.RoleType;
import com.auth.feature.user.service.PrivilegeService;
import com.auth.feature.user.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Prepopulate sample data.
 */

@Component
public class DataInitializer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    PrivilegeService privilegeService;

    @Inject
    RoleService roleService;

    @PostConstruct
    void init() {
        // Create privileges.
        Privilege readPrivilege = this.createPrivilege(PrivilegeType.READ);
        Privilege writePrivilege = this.createPrivilege(PrivilegeType.WRITE);
        // Create roles.
        List<Privilege> adminPrivilege = Arrays.asList(readPrivilege, writePrivilege);
        Role userRole = this.createRole(RoleType.ROLE_USER, Arrays.asList(readPrivilege));
        Role adminRole = this.createRole(RoleType.ROLE_ADMIN, adminPrivilege);

        // Update read privilege with user role.
        Privilege read = this.privilegeService.getPrivilegeByType(PrivilegeType.READ);
        read.getRoles().add(userRole);
        this.privilegeService.savePrivilege(read);

        // Update write privilege with admin role.
        Privilege write = this.privilegeService.getPrivilegeByType(PrivilegeType.WRITE);
        write.getRoles().add(adminRole);
        this.privilegeService.savePrivilege(write);
    }

    /**
     * Create privilege data.
     *
     * @param type
     * @return privilege
     */
    Privilege createPrivilege(PrivilegeType type) {
        Privilege privilege = new Privilege();
        privilege.setType(type);
        return this.privilegeService.savePrivilegeAndFlush(privilege);
    }

    /**
     * Create role associated with privilege.
     *
     * @param roleType
     * @param privileges
     * @return role
     */
    Role createRole(RoleType roleType, List<Privilege> privileges) {
        Role role = new Role();
        role.setType(roleType);
        role.setPrivileges(privileges);
        return this.roleService.saveRoleAndFlush(role);
    }
}
