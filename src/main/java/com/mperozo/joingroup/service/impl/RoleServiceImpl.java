package com.mperozo.joingroup.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mperozo.joingroup.exception.BusinessException;
import com.mperozo.joingroup.model.entity.Role;
import com.mperozo.joingroup.model.enums.RolesEnum;
import com.mperozo.joingroup.model.repository.RoleRepository;
import com.mperozo.joingroup.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
	
	Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@Override
	public Role buscarPorNome(RolesEnum roleEnum) {

		Optional<Role> role = roleRepository.findByNome(roleEnum);
		
		if(!role.isPresent()) {
			throw new BusinessException("Role não encontrada na base de dados para o nome = " + roleEnum.name() );
		}
		
		return role.get();
	}
	
	@Override
	@Transactional
	public Role salvarRole(RolesEnum roleEnum) {
		
		Role role = new Role(roleEnum);
		
		return roleRepository.save(role);
	}

}
