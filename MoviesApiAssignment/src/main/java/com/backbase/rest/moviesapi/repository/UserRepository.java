package com.backbase.rest.moviesapi.repository;

import com.backbase.rest.moviesapi.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByName(String name);
}
