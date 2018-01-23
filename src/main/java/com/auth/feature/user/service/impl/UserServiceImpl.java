package com.auth.feature.user.service.impl;

import com.auth.core.exception.global.PatchOperationNotSupported;
import com.auth.core.rest.patch.Patch;
import com.auth.core.rest.patch.PatchEnum;
import com.auth.feature.user.exception.EmailNotFoundException;
import com.auth.feature.user.exception.UserNotFoundException;
import com.auth.feature.user.model.Privilege;
import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.RoleDto;
import com.auth.feature.user.model.dto.UserDto;
import com.auth.feature.user.model.enums.PrivilegeEnum;
import com.auth.feature.user.repository.UserRepository;
import com.auth.feature.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        UserInfo user = this.userRepository.findByEmail(email);
        // Throw exception if user not found.
        if (user == null) {
            throw new EmailNotFoundException("Email " + email + " not found");
        }
        return new User(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, getAuthorities(user.getRoles()));
    }

    /**
     * Get user privileges and roles.
     *
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getType().name()));
            role.getPrivileges()
                    .stream()
                    .map(p -> new SimpleGrantedAuthority(p.getType().name()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public UserInfo saveUser(UserInfo user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserInfo saveUserAndFlush(UserInfo user) {
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserInfo updateUser(Long id, UserDto dto) throws UserNotFoundException {
        UserInfo user = this.userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        return this.userRepository.save(user);
    }

    @Override
    public void removeUserById(Long id) throws UserNotFoundException {
        UserInfo user = this.userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        this.userRepository.delete(id);
    }

    @Override
    public List<UserInfo> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public UserInfo getUserByEmail(String email) throws UserNotFoundException {
        UserInfo user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserInfo getUserById(Long id) throws UserNotFoundException {
        UserInfo user = this.userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserInfo patchUser(Long id, Patch patch) throws PatchOperationNotSupported, UserNotFoundException {
        if (!patch.getPatchEnum().equals(PatchEnum.REPLACE)) {
            throw new PatchOperationNotSupported("Patch operation not supported");
        }
        UserInfo user = this.userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (patch.getField().equals("email")) {
            user.setEmail(patch.getValue());
        } else if (patch.getField().equals("password")) {
            user.setPassword(patch.getValue());
        } else if (patch.getField().equals("firstName")) {
            user.setLastName(patch.getValue());
        } else if (patch.getField().equals("lastName")) {
            user.setLastName(patch.getValue());
        } else if (patch.getField().equals("enabled")) {
            user.setEnabled(Boolean.parseBoolean(patch.getValue()));
        }
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean isEmailAlreadyExist(String email) {
        UserInfo user = this.userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserInfo addUserRole(Long id, RoleDto dto) throws UserNotFoundException {
        UserInfo user = this.userRepository.findOne(id);
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }
        // Add privileges.
        List<Privilege> privileges = new ArrayList<>();
        for(PrivilegeEnum p : dto.getPrivilegeEnums()) {
            privileges.add(new Privilege(p));
        }
        // Set role.
        Role role = new Role();
        role.setType(dto.getRoleEnum());
        role.setPrivileges(privileges);
        // Update user role.
        user.setRole(role);
        // Update user, cascade is added in role and user to affect child entity.
        return this.userRepository.saveAndFlush(user);
    }
}
