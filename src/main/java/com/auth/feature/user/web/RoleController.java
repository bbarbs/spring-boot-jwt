package com.auth.feature.user.web;

import com.auth.core.rest.response.ApiResponse;
import com.auth.feature.user.model.UserInfo;
import com.auth.feature.user.model.dto.RoleDto;
import com.auth.feature.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RoleController {

    @Inject
    UserService userService;

    /**
     * Add user role.
     *
     * @param userId
     * @param roleDto
     * @return
     */
    @PostMapping(
            value = "/users/{userId}/roles",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserInfo> addUserRole(@PathVariable(name = "userId") Long userId,
                                             @RequestBody RoleDto roleDto) {
        UserInfo info = this.userService.addUserRole(userId, roleDto);
        return new ApiResponse<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                Arrays.asList(info));
    }
}
