package com.mperozo.joingroup.service;

import com.mperozo.joingroup.model.entity.Role;
import com.mperozo.joingroup.model.enums.RolesEnum;

public interface RoleService {

	Role salvarRole(RolesEnum roleEnum);

	Role buscarPorNome(RolesEnum roleEnum);
}
