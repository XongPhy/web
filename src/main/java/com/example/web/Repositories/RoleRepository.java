package com.example.web.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findRoleByName(String name);
}
