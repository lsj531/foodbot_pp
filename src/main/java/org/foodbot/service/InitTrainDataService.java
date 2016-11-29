package org.foodbot.service;

import java.util.List;

import org.foodbot.domain.BoardVO;
import org.foodbot.domain.Criteria;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.SearchCriteria;


public interface InitTrainDataService {
	public void create(InitTrainDataVO vo) throws Exception;
	public InitTrainDataVO read() throws Exception;
	public void update(InitTrainDataVO vo) throws Exception;
	public List<InitTrainDataVO> listAll() throws Exception;
	public void delete(Integer tno) throws Exception;
}
