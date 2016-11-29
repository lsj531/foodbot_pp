package org.foodbot.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.ManagerVO;
import org.foodbot.dto.LoginDTO;
import org.springframework.stereotype.Repository;

@Repository
public class InitTrainDataDAOImpl implements InitTrainDataDAO{
	
	@Inject
	SqlSession session;
	
	private static String namespace = "org.foodbot.mapper.InitTrainDataMapper";

	@Override
	public void create(InitTrainDataVO vo) throws Exception {
		session.insert(namespace+".create",vo);		
		
	}
	@Override
	public InitTrainDataVO read() throws Exception {
		return session.selectOne(namespace+".read");
	}
	@Override
	public List<InitTrainDataVO> listAll() throws Exception {
		return session.selectList(namespace+".listAll");
	}
	@Override
	public void delete(Integer tno) throws Exception {
		session.delete(namespace+".delete",tno);
	}
	@Override
	public void update(InitTrainDataVO vo) throws Exception {
		session.update(namespace+".update",vo);
	}

}
