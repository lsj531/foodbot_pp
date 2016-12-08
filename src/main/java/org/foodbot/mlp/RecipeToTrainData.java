package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

import org.foodbot.nlp.RecipeVO;

// Create train data using recipe DB.
public class RecipeToTrainData {

	private List<RecipeVO> trainInputList;
	private int[] trainOuputData;

	private List<RecipeVO> recipeList;
	private List<String> foodName;

	private RecipeVO recipeVO;
	private List<String> RMajorAttrList;
	private List<String> RSubAttrList;
	private List<String> RTasteAttrList;


	public RecipeToTrainData() {}

	public RecipeToTrainData(List<RecipeVO> recipeList, List<String> foodName) {
		this.recipeList = recipeList;
		this.foodName = foodName;
		setData();
	}

	private void setData() {
		List<Integer> tmpOutputList = new ArrayList<Integer>();
		trainInputList = new ArrayList<RecipeVO>();
		for(int i=0; i<recipeList.size() ; i++) {

			// 주재료 + 부재료 + 맛
			recipeVO = new RecipeVO();
			if(recipeList.get(i).getRMajorAttrList() != null)
				recipeVO.setRMajorAttrList(recipeList.get(i).getRMajorAttrList());
			if(recipeList.get(i).getRSubAttrList() != null)
				recipeVO.setRSubAttrList(recipeList.get(i).getRSubAttrList());
			if(recipeList.get(i).getRTasteAttrList() != null)
				recipeVO.setRTasteAttrList(recipeList.get(i).getRTasteAttrList());
			trainInputList.add(recipeVO);
			tmpOutputList.add(i);

			// 주재료 + 부재료
//			recipeVO = new RecipeVO();
//			if(recipeList.get(i).getRMajorAttrList() != null)
//				recipeVO.setRMajorAttrList(recipeList.get(i).getRMajorAttrList());
//			if(recipeList.get(i).getRSubAttrList() != null)
//				recipeVO.setRSubAttrList(recipeList.get(i).getRSubAttrList());
//			recipeVO.setRTasteAttrList(new ArrayList<String>());
//			trainInputList.add(recipeVO);
//			tmpOutputList.add(i);
			/*
			// 주재료 + 맛
			recipeVO = new RecipeVO();
			if(recipeList.get(i).getRMajorAttrList() != null)
				recipeVO.setRMajorAttrList(recipeList.get(i).getRMajorAttrList());
			if(recipeList.get(i).getRTasteAttrList() != null)
				recipeVO.setRTasteAttrList(recipeList.get(i).getRTasteAttrList());
			recipeVO.setRSubAttrList(new ArrayList<String>());
			trainInputList.add(recipeVO);
			tmpOutputList.add(i);


			// 주재료
			recipeVO = new RecipeVO();
			if(recipeList.get(i).getRMajorAttrList() != null)
				recipeVO.setRMajorAttrList(recipeList.get(i).getRMajorAttrList());
			recipeVO.setRSubAttrList(new ArrayList<String>());
			recipeVO.setRTasteAttrList(new ArrayList<String>());
			trainInputList.add(recipeVO);
			tmpOutputList.add(i);

			// 부재료 + 맛
			recipeVO = new RecipeVO();
			if(recipeList.get(i).getRSubAttrList() != null)
				recipeVO.setRSubAttrList(recipeList.get(i).getRSubAttrList());
			if(recipeList.get(i).getRTasteAttrList() != null)
				recipeVO.setRTasteAttrList(recipeList.get(i).getRTasteAttrList());
			recipeVO.setRMajorAttrList(new ArrayList<String>());
			trainInputList.add(recipeVO);
			tmpOutputList.add(i);
			 */
		}

		trainOuputData = new int[tmpOutputList.size()];
		for(int i=0  ; i<tmpOutputList.size() ; i++) {
			trainOuputData[i] = tmpOutputList.get(i);
		}

	}

	public List<RecipeVO> getTrainInputList() {
		return trainInputList;
	}

	public void setTrainInputList(List<RecipeVO> trainInputList) {
		this.trainInputList = trainInputList;
	}

	public int[] getTrainOuputData() {
		return trainOuputData;
	}

	public void setTrainOuputData(int[] trainOuputData) {
		this.trainOuputData = trainOuputData;
	}


}
