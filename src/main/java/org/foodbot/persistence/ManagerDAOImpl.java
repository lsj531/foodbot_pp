package org.foodbot.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.foodbot.domain.ManagerVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.dto.LoginDTO;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAOImpl implements ManagerDAO{
	
	@Inject
	SqlSession session;
	
	private static String namespace = "org.foodbot.mapper.ManagerMapper";

	@Override
	public ManagerVO read(Integer mno) throws Exception {
		return session.selectOne(namespace+".read",mno);
	}

	@Override
	public ManagerVO login(LoginDTO dto) throws Exception {
		return session.selectOne(namespace + ".login",dto);
	}

}
