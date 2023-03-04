package com.backbase.rest.moviesapi.controller;

import javax.annotation.Resource;
import java.util.List;

import com.backbase.rest.moviesapi.domain.AppUser;
import com.backbase.rest.moviesapi.domain.AppRole;
import com.backbase.rest.moviesapi.exception.ErrorResponse;
import com.backbase.rest.moviesapi.service.UserService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/")
public class UserController {
    @Resource
    private UserService userService;

    @Operation(summary = "Get List of All Registered Users; Role Required: ROLE_MANAGER or ROLE_ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description =" Return the List of Users",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUser.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @Operation(summary = "Register the new user; Role Required: ROLE_MANAGER or ROLE_ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201",description ="Return the created User",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUser.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/user/save")
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser appUser){
        return ResponseEntity.created(null).body(userService.saveUser(appUser));
    }

    @Operation(summary = "Register the new Role; Role Can be (ROLE_MANAGER or ROLE_ADMIN or ROLE_USER), Role Required: ROLE_MANAGER or ROLE_ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201",description ="Return the created Role",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppRole.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/role/save")
    public ResponseEntity<AppRole>saveRole(@RequestBody AppRole appRole){
        return ResponseEntity.created(null).body(userService.saveRole(appRole));
    }

    @Operation(summary = "Add Role to the User; Role Required: ROLE_MANAGER or ROLE_ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description ="Empty Response",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addUserToRole(@RequestBody RoleToUserForm roleToUserForm){
         userService.addRoleToUser(roleToUserForm.getUserName(), roleToUserForm.getRoleName());
         return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm{
    private String userName;
    private String roleName;
}
