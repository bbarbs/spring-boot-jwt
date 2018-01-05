package com.auth.feature.user.repository;

import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleType type);
}
