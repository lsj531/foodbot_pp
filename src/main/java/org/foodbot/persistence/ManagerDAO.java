package org.foodbot.persistence;

import org.foodbot.domain.ManagerVO;
import org.foodbot.dto.LoginDTO;


public interface ManagerDAO {
	public ManagerVO read(Integer mno) throws Exception;
	public ManagerVO login(LoginDTO dto) throws Exception;
	
}
