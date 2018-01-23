package com.auth.feature.user.repository;

import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.enums.PrivilegeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByType(PrivilegeEnum type);
}
