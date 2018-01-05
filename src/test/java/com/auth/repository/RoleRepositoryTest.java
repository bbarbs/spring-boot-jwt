package com.auth.repository;

import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.enums.RoleType;
import com.auth.feature.user.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Inject
    TestEntityManager entityManager;

    @Inject
    RoleRepository roleRepository;

    @Test
    public void testShouldFindRoleByType() {
        Role role = new Role();
        role.setType(RoleType.ROLE_ADMIN);
        role.setUsers(new ArrayList<>());
        role.setPrivileges(new ArrayList<>());
        this.entityManager.persistAndFlush(role);
        Role result = this.roleRepository.findByType(RoleType.ROLE_ADMIN);
        assertThat(role.getType()).isEqualTo(result.getType());
    }
}
