package com.mperozo.joingroup.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mperozo.joingroup.model.entity.Role;
import com.mperozo.joingroup.model.enums.RolesEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByNome(RolesEnum roleEnum);
}
