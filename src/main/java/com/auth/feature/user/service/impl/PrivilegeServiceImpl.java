package com.auth.feature.user.service.impl;

import com.auth.feature.user.exception.PrivilegeNotFoundException;
import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.enums.PrivilegeType;
import com.auth.feature.user.repository.PrivilegeRepository;
import com.auth.feature.user.service.PrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

    @Inject
    PrivilegeRepository privilegeRepository;

    @Override
    public Privilege savePrivilege(Privilege privilege) {
        return this.privilegeRepository.save(privilege);
    }

    @Override
    public Privilege savePrivilegeAndFlush(Privilege privilege) {
        return this.privilegeRepository.saveAndFlush(privilege);
    }

    @Override
    public Privilege getPrivilegeByType(PrivilegeType type) {
        Privilege privilege = this.privilegeRepository.findByType(type);
        if (privilege == null) {
            throw new PrivilegeNotFoundException("Privilege not found");
        }
        return privilege;
    }

    @Override
    public Privilege getPrivilegeById(Long id) {
        Privilege privilege = this.privilegeRepository.findOne(id);
        if (privilege == null) {
            throw new PrivilegeNotFoundException("Privilege not found");
        }
        return privilege;
    }
}
