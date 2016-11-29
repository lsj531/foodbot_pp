package org.foodbot.service;

import java.util.List;

import org.foodbot.domain.ManagerVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.dto.LoginDTO;

public interface ManagerService {
	public ManagerVO read(Integer mno) throws Exception;
	public ManagerVO login(LoginDTO dto) throws Exception;
}
