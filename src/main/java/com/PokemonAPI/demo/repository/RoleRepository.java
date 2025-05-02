package com.PokemonAPI.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.PokemonAPI.demo.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}