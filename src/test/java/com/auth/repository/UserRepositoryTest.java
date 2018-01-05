package com.auth.repository;

import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.repository.UserRepository;
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
public class UserRepositoryTest {

    @Inject
    private TestEntityManager entityManager;

    @Inject
    private UserRepository userRepository;

    @Test
    public void testShouldFindUserByEmail() {
        UserInfo user = new UserInfo();
        user.setEmail("test@gmail.com");
        user.setPassword("pass");
        user.setLastName("Test");
        user.setFirstName("Test");
        user.setEnabled(true);
        user.setRoles(new ArrayList<>());
        this.entityManager.persistAndFlush(user);
        UserInfo result = this.userRepository.findByEmail(user.getEmail());
        assertThat(user.getEmail()).isEqualTo(result.getEmail());
    }
}
