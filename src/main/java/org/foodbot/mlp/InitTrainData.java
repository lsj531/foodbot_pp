package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.foodbot.domain.FoodVO;
import org.foodbot.nlp.RecipeVO;
import org.foodbot.service.FoodService;

/*
 * Management authority
 * get InitTrainData for init train
 */
public class InitTrainData {

	@Inject 
	private FoodService fservice;

	private Double[] input_value;
	private List<Double> input;

	private List<FoodVO> recipeAll;
	private List<String> dataName;
	private List<String> foodName;

	// db의 모든 레시피 리스트
	private List<RecipeVO> recipeList;

	// 최종 분류기 입력값
	private List<ArrayList<Double>> trainDataList;
	private List<Double> tempList;
	// 최종 분류기 출력 레이블값
	private int[] trainOuputData;

	public InitTrainData(FoodService fservice) throws Exception {
		this.fservice = fservice;

		trainDataList = new ArrayList<ArrayList<Double>>();
		recipeAll = fservice.readRecipeAll();

		// input 갯수 만큼의 배열크기로 만듬
		input_value = new Double[HyperParameter.INPUT_NUM];
		// input 초기값을 불러옴
		input = HyperParameter.input;


		// 모든 레시피를 불러옴
		InitRecipe ir = new InitRecipe(recipeAll);
		recipeList = ir.getRecipeList();

		// 분류기 입력노드 (속성) 불러옴
		TrainDataName trainData = new TrainDataName(fservice);
		dataName = trainData.getDataName();
		foodName = trainData.getOutputName();
		
		// 레시피를 조합하여 분류기 입력출력 데이터를 만든다.
		RecipeToTrainData rtt = new RecipeToTrainData(recipeList,foodName);
		recipeList = rtt.getTrainInputList();
		trainOuputData = rtt.getTrainOuputData();

		System.out.println("dataName " + dataName.size() );
		System.out.println("foodName " + foodName.size() );

		// 분류기 입력노드 순서 출력
		//		for(int i= 0 ; i<dataName.size() ; i++) {
		//			System.out.print(i+dataName.get(i) + "    ");
		//		}System.out.println();

		// 모든 레시피 출력
		System.out.println("모든 레시피");
		for(int i=0 ; i<recipeList.size() ;i++) {
			for(int j=0 ; j<recipeList.get(i).getRMajorAttrList().size() ; j++) {
				System.out.print(recipeList.get(i).getRMajorAttrList().get(j) + "   ");
			}
			for(int j=0 ; j<recipeList.get(i).getRSubAttrList().size() ; j++) {
				System.out.print(recipeList.get(i).getRSubAttrList().get(j) + "   ");
			}
			for(int j=0 ; j<recipeList.get(i).getRTasteAttrList().size() ; j++) {
				System.out.print(recipeList.get(i).getRTasteAttrList().get(j) + "   ");
			}System.out.println();
		}System.out.println();

		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		for(int i=0 ; i<recipeList.size() ; i++) {
			tempList = new ArrayList<Double>();
			System.out.println("dataName" + dataName.size());
			for(int j=0 ; j<dataName.size(); j++) {
				flag1 = true;
				flag2 = true;
				flag3 = true;
				for(int k=0 ;k<recipeList.get(i).getRMajorAttrList().size() ; k++) {
					if(dataName.get(j).equals(recipeList.get(i).getRMajorAttrList().get(k))) {
						tempList.add(HyperParameter.INIT_VALUE_07);
						flag1 = false;
						break;
					} else {
						flag1 = true;
					}
				}
				for(int k=0 ;k<recipeList.get(i).getRSubAttrList().size() ; k++) {
					if(dataName.get(j).equals(recipeList.get(i).getRSubAttrList().get(k))) {
						tempList.add(HyperParameter.INIT_VALUE_03);
						flag2 = false;
						break;
					} else {
						flag2 = true;
					}
				}
				for(int k=0 ;k<recipeList.get(i).getRTasteAttrList().size() ; k++) {
					if(dataName.get(j).equals(recipeList.get(i).getRTasteAttrList().get(k))) {
						tempList.add(HyperParameter.INIT_VALUE_08);
						flag3 = false;
						break;
					} else {
						flag3 = true;
					}
				}

				if(flag1 == true && flag2 == true && flag3 == true) {
					tempList.add(HyperParameter.INIT_VALUE_0);
					
				}
			}
			trainDataList.add((ArrayList<Double>) tempList);
			tempList = null;
		}

		// 분류기에 입력할 최종값 위치 출력
		for(int i=0 ; i<trainDataList.size(); i++) {
			System.out.print(i + " : " + trainDataList.get(i).size() + " : ");
			for(int j=0 ; j<trainDataList.get(i).size() ; j++) {
				if(trainDataList.get(i).get(j) != 0.0) {
					System.out.print(j + "   ");
				}
			}
			System.out.println();
		}
		System.out.println("노멀 전");
		for(int i=0 ; i<trainDataList.size(); i++) {
			System.out.println(i);
			for(int j=0 ; j<trainDataList.get(i).size() ; j++) {
				System.out.print(trainDataList.get(i).get(j)+ " ");
			}System.out.println();
		}
		
		/*
		 * 
		 * nomalization 일단안함
		 */ 
//		MLPFunction mlpFunc = new MLPFunction();
//		trainDataList = mlpFunc.setNomalization(trainDataList);
		
	}

	public List<ArrayList<Double>> getTrainDataList() {
		return trainDataList;
	}

	public void setTrainDataList(List<ArrayList<Double>> trainDataList) {
		this.trainDataList = trainDataList;
	}

	public int[] getTrainOuputData() {
		return trainOuputData;
	}

	public void setTrainOuputData(int[] trainOuputData) {
		this.trainOuputData = trainOuputData;
	}

}
