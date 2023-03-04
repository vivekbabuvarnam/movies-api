package com.backbase.rest.moviesapi.repository;

import com.backbase.rest.moviesapi.domain.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByName(String name);
}
