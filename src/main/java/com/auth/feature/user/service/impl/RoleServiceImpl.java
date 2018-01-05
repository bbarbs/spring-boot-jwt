package com.auth.feature.user.service.impl;

import com.auth.feature.user.exception.RoleNotFoundException;
import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.enums.RoleType;
import com.auth.feature.user.repository.RoleRepository;
import com.auth.feature.user.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Inject
    RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Role saveRoleAndFlush(Role role) {
        return this.roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getRoleByType(RoleType type) {
        Role role = this.roleRepository.findByType(type);
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = this.roleRepository.findOne(id);
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return role;
    }
}
