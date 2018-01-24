package com.auth.feature.user.web;

import com.auth.core.rest.patch.Patch;
import com.auth.core.rest.response.ApiResponse;
import com.auth.core.util.RestUtil;
import com.auth.feature.user.exception.EmailExistsException;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.UserDto;
import com.auth.feature.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
public class UserController {

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Inject
    UserService userService;

    @Inject
    RestUtil restUtil;

    /**
     * Get list of user.
     *
     * @return
     */
    @GetMapping(
            value = "/users",
            produces = APPLICATION_JSON_VALUE
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
            value = "/users",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ApiResponse addUser(@RequestBody UserDto dto) throws EmailExistsException {
        boolean isEmailExist = this.userService.isEmailAlreadyExist(dto.getEmail());
        // Check if email exist.
        if (isEmailExist) {
            throw new EmailExistsException("Email " + dto.getEmail() + " already exists");
        }
        UserInfo user = new UserInfo();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setEmail(dto.getEmail());
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
            value = "/users/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasAuthority('READ')"
    )
    public UserInfo getUserById(@PathVariable int id) {
        return this.userService.getUserById(Long.valueOf(id));
    }

    /**
     * Delete user by id.
     *
     * @param id
     * @return
     */
    @DeleteMapping(
            value = "/users/{id}",
            produces = TEXT_PLAIN_VALUE
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
            value = "/users/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ApiResponse updateUserById(@PathVariable int id, @RequestBody UserDto dto) {
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
            value = "/users/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ApiResponse patchUser(@PathVariable int id, @RequestBody Patch patch) {
        UserInfo res = this.userService.patchUser(Long.valueOf(id), patch);
        return this.restUtil.createApiResponse(HttpStatus.CREATED.value(),
                HttpStatus.CREATED, Arrays.asList(res));
    }
}