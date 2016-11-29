package org.foodbot.service;

import javax.inject.Inject;

import org.foodbot.domain.MLPWeightVO;
import org.foodbot.persistence.MLPWeightDAO;
import org.springframework.stereotype.Service;


@Service
public class MLPWeightServiceImpl implements MLPWeightService {
	
	@Inject
	private MLPWeightDAO dao;

	@Override
	public void create(MLPWeightVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public MLPWeightVO read(String uid) throws Exception {
		return dao.read(uid);
	}

	@Override
	public void updateWeight(MLPWeightVO vo) throws Exception {
		dao.updateWeight(vo);
	}

	@Override
	public void updateAttr(MLPWeightVO vo) throws Exception {
		dao.updateAttr(vo);
	}

	@Override
	public void updateLearningB(MLPWeightVO vo) throws Exception {
		dao.updateLearningB(vo);
	}

	@Override
	public void updateLearningCURR(MLPWeightVO vo) throws Exception {
		dao.updateLearningCURR(vo);
	}

	


}
