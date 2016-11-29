package org.foodbot.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.domain.ManagerVO;
import org.foodbot.dto.LoginDTO;
import org.springframework.stereotype.Repository;

@Repository
public class MLPWeightDAOImpl implements MLPWeightDAO{
	
	@Inject
	SqlSession session;
	
	private static String namespace = "org.foodbot.mapper.MLPWeightMapper";

	@Override
	public void create(MLPWeightVO vo) throws Exception {
		session.insert(namespace+".create",vo);
	}

	@Override
	public MLPWeightVO read(String uid) throws Exception {
		return session.selectOne(namespace+".read",uid);
	}

	@Override
	public void updateWeight(MLPWeightVO vo) throws Exception {
		session.update(namespace+".updateWeight",vo);
	}

	@Override
	public void updateAttr(MLPWeightVO vo) throws Exception {
		session.update(namespace+".updateAttr",vo);
	}

	@Override
	public void updateLearningB(MLPWeightVO vo) throws Exception {
		session.update(namespace+".updateLearningB",vo);
	}

	@Override
	public void updateLearningCURR(MLPWeightVO vo) throws Exception {
		session.update(namespace+".updateLearningCURR",vo);
	}

	
}
