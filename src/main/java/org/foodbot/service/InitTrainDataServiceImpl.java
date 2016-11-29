package org.foodbot.service;

import java.util.List;

import javax.inject.Inject;

import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.persistence.InitTrainDataDAO;
import org.springframework.stereotype.Service;


@Service
public class InitTrainDataServiceImpl implements InitTrainDataService {
	
	@Inject
	private InitTrainDataDAO dao;

	@Override
	public void create(InitTrainDataVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public InitTrainDataVO read() throws Exception {
		return dao.read();
	}

	@Override
	public List<InitTrainDataVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public void delete(Integer tno) throws Exception {
		dao.delete(tno);
	}

	@Override
	public void update(InitTrainDataVO vo) throws Exception {
		dao.update(vo);
	}



}
