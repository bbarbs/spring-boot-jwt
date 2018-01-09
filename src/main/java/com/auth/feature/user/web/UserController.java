package com.auth.feature.user.web;

import com.auth.core.rest.patch.Patch;
import com.auth.core.rest.response.ResponseMessage;
import com.auth.core.util.RestUtil;
import com.auth.feature.user.exception.EmailExistsException;
import com.auth.feature.user.exception.UserNotFoundException;
import com.auth.feature.user.model.Role;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.UserDto;
import com.auth.feature.user.model.enums.RoleType;
import com.auth.feature.user.service.RoleService;
import com.auth.feature.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Inject
    UserService userService;

    @Inject
    RoleService roleService;

    @Inject
    RestUtil restUtil;

    /**
     * Get list of user.
     *
     * @return
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN')"
    )
    public List<UserInfo> getAllUsers() {
        return this.userService.getAllUser();
    }

    /**
     * Add new user.
     *
     * @param dto
     * @return
     */
    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseMessage registerUser(@RequestBody UserDto dto) {
        boolean isEmailExist = this.userService.isEmailAlreadyExist(dto.getEmail());
        // Check if email exist.
        if (isEmailExist) {
            throw new EmailExistsException("Email " + dto.getEmail() + " already exists");
        }
        Role role = this.roleService.getRoleByType(RoleType.ROLE_ADMIN);
        UserInfo user = new UserInfo();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setEmail(dto.getEmail());
        user.setRoles(Arrays.asList(role));
        // Save user.
        UserInfo res = this.userService.saveUserAndFlush(user);
        return this.restUtil.createApiResponse(HttpStatus.CREATED.value(),
                HttpStatus.CREATED, Arrays.asList(res));
    }

    /**
     * Get user by id.
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasAuthority('READ')"
    )
    public UserInfo getUserById(@PathVariable int id) {
        UserInfo userInfo = this.userService.getUserById(Long.valueOf(id));
        if (userInfo == null) {
            throw new UserNotFoundException("User not found");
        }
        return userInfo;
    }

    /**
     * Delete user by id.
     *
     * @param id
     * @return
     */
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        this.userService.removeUserById((long) id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update user by id.
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ResponseMessage updateUserById(@PathVariable int id, @RequestBody UserDto dto) {
        UserInfo res = this.userService.updateUser(Long.valueOf(id), dto);
        return this.restUtil.createApiResponse(HttpStatus.CREATED.value(),
                HttpStatus.CREATED, Arrays.asList(res));
    }

    /**
     * Patch user.
     *
     * @param id
     * @param patch
     * @return
     */
    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ResponseMessage patchUser(@PathVariable int id, @RequestBody Patch patch) {
        UserInfo res = this.userService.patchUser(Long.valueOf(id), patch);
        return this.restUtil.createApiResponse(HttpStatus.CREATED.value(),
                HttpStatus.CREATED, Arrays.asList(res));
    }
}