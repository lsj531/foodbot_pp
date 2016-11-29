package org.foodbot.persistence;

import java.util.List;

import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;


public interface MLPWeightDAO {
	
	public void create(MLPWeightVO vo) throws Exception;
	public MLPWeightVO read(String uid) throws Exception;
	public void updateWeight(MLPWeightVO vo) throws Exception;
	public void updateAttr(MLPWeightVO vo) throws Exception;
	public void updateLearningB(MLPWeightVO vo) throws Exception;
	public void updateLearningCURR(MLPWeightVO vo) throws Exception;
	
	
}
