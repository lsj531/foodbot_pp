package org.foodbot.nlp;

import java.util.List;

import org.foodbot.domain.FoodVO;

public interface Recipe {

	 List<String> getMajorRecipe() throws Exception;
	 List<String> getSubRecipe() throws Exception;
	 List<String> getTasteRecipe() throws Exception;
	 List<String> getRecipe() throws Exception;
	 void setRAttrList(List<FoodVO> flist) throws Exception;
}
