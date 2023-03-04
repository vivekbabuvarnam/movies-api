package com.backbase.rest.moviesapi.service;

import java.util.List;

import com.backbase.rest.moviesapi.domain.AppRole;
import com.backbase.rest.moviesapi.domain.AppUser;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    AppRole saveRole(AppRole appRole);

    void addRoleToUser(String userName,String roleName);
    AppUser getUser(String userName);
    List<AppUser> getUsers();
}
