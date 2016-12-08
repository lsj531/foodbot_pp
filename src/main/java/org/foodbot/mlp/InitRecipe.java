package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

import org.foodbot.domain.FoodVO;
import org.foodbot.nlp.Recipe;
import org.foodbot.nlp.RecipeVO;

public class InitRecipe implements Recipe {

	// 레시피의 재료가 다르기때문에 이중리스트를 만듬
	private List<RecipeVO> recipeList;
	private List<String> RAttrList;
	private List<String> RMajorAttrList;
	private List<String> RSubAttrList;
	private List<String> RTasteAttrList;
	private List<String> RFoodNameList;

	private List<FoodVO> recipeAll;

	public InitRecipe(List<FoodVO> recipeAll) throws Exception {
		this.recipeAll = recipeAll;

		recipeList = new ArrayList<RecipeVO>();

		// 속성 리턴
		setRAttrList(recipeAll);
		// 음식명 리턴
		setFNameList(recipeAll);
	}

	public void setFNameList(List<FoodVO> flist) {
		RFoodNameList = new ArrayList<String>();
		for (int i = 0; i < flist.size(); i++) {
			RFoodNameList.add(flist.get(i).getFname());
		}
	}

	@Override
	public void setRAttrList(List<FoodVO> flist) throws Exception {
		String[] majorStr = { "" };
		String[] tasteStr = { "" };
		String[] subStr = { "" };
		String[] rStr = { "" };
		String[] temp = { "" };

		for (int i = 0; i < flist.size(); i++) {
			if (majorStr != null)
				majorStr = null;
			if (subStr != null)
				subStr = null;
			if (tasteStr != null)
				tasteStr = null;
			if (temp != null)
				temp = null;

			if (flist.get(i).getIngredset() != null) {
				temp = flist.get(i).getIngredset().trim().split("\\|");
				majorStr = temp[0].split(",");
				subStr = temp[1].split(",");
			}
			if (flist.get(i).getTasteset() != null) {
				tasteStr = flist.get(i).getTasteset().split(",");
			}

			// 각 리스트별로 레시피 리스트를 넣는다.
			RAttrList = new ArrayList<String>();
			RMajorAttrList = new ArrayList<String>();
			RSubAttrList = new ArrayList<String>();
			RTasteAttrList = new ArrayList<String>();

			inputRAttr(majorStr, subStr, tasteStr);
		}
	}

	private void inputRAttr(String[] majorStr, String[] subStr, String[] tasteStr) {

		for (int i = 0; i < majorStr.length; i++) {
			if (!majorStr[i].trim().equals("") && !majorStr[i].trim().equals(null)) {
				RMajorAttrList.add(majorStr[i]);
				// RAttrList.add(rStr[i]);
			}
		}
		for (int i = 0; i < subStr.length; i++) {
			if (!subStr[i].trim().equals("") && !subStr[i].trim().equals(null)) {
				RSubAttrList.add(subStr[i]);
				// RAttrList.add(rStr[i]);
			}
		}
		for (int i = 0; i < tasteStr.length; i++) {
			if (!tasteStr[i].trim().equals("") && !tasteStr[i].trim().equals(null)) {
				RTasteAttrList.add(tasteStr[i]);
				// RAttrList.add(rStr[i]);
			}
		}

		RecipeVO vo = new RecipeVO();
		vo.setRMajorAttrList(RMajorAttrList);
		vo.setRSubAttrList(RSubAttrList);
		vo.setRTasteAttrList(RTasteAttrList);
		recipeList.add(vo);
	}

	public List<RecipeVO> getRecipeList() {
		return recipeList;
	}

	public void setRecipeList(List<RecipeVO> recipeList) {
		this.recipeList = recipeList;
	}

	@Override
	public List<String> getMajorRecipe() throws Exception {
		return RMajorAttrList;
	}

	@Override
	public List<String> getSubRecipe() throws Exception {
		return RSubAttrList;
	}

	@Override
	public List<String> getTasteRecipe() throws Exception {
		return RTasteAttrList;
	}

	@Override
	public List<String> getRecipe() throws Exception {
		return RAttrList;
	}

	public List<String> getRFoodNameList() {
		return RFoodNameList;
	}

	public void setRFoodNameList(List<String> rFoodNameList) {
		RFoodNameList = rFoodNameList;
	}
}
