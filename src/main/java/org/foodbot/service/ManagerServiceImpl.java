package org.foodbot.service;

import javax.inject.Inject;

import org.foodbot.domain.ManagerVO;
import org.foodbot.dto.LoginDTO;
import org.foodbot.persistence.ManagerDAO;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Inject
	ManagerDAO dao;

	@Override
	public ManagerVO read(Integer mno) throws Exception {
		return dao.read(mno);
	}

	@Override
	public ManagerVO login(LoginDTO dto) throws Exception {
		return dao.login(dto);
	}
	
	
	

}
