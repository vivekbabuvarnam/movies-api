package com.backbase.rest.moviesapi.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import com.backbase.rest.moviesapi.domain.AppRole;
import com.backbase.rest.moviesapi.domain.AppUser;
import com.backbase.rest.moviesapi.repository.RoleRepository;
import com.backbase.rest.moviesapi.repository.UserRepository;
import com.backbase.rest.moviesapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByName(username);
        if(appUser == null)
        {
            log.error("User Not Found in database");
            throw new UsernameNotFoundException("User Not Found in database");
        }
        else
        {
            log.info("User Found in Database: {} ", username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getAppRoles().stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(appUser.getName(), appUser.getPassword(), authorities );
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return roleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser = userRepository.findByName(userName);
        AppRole appRole = roleRepository.findByName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser getUser(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

}
