package org.foodbot.persistence;

import java.util.List;

import org.foodbot.domain.BoardVO;
import org.foodbot.domain.Criteria;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.ManagerVO;
import org.foodbot.domain.SearchCriteria;
import org.foodbot.dto.LoginDTO;
import org.foodbot.mlp.InitTrain;


public interface InitTrainDataDAO {
	
	public void create(InitTrainDataVO vo) throws Exception;
	public InitTrainDataVO read() throws Exception;
	public void update(InitTrainDataVO vo) throws Exception;
	public List<InitTrainDataVO> listAll() throws Exception;
	public void delete(Integer tno) throws Exception;
	
	
}
