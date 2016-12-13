package org.foodbot.mlp;

import java.util.List;

import javax.inject.Inject;

import org.foodbot.domain.FoodVO;
import org.foodbot.service.FoodService;

/*
 * 선행조건 : weight 초기화
 * feedFoward를 통해
 * 결과 음식을 출력시켜준다.
 */
public class Classification {
	private FeedFoward feedFoward;
	private List<double[][]> activationList;
	private int[] dataSet;
	private double[] result;
	private double[][] resultList;
	
	@Inject
	private FoodService fservice;
	
	public Classification(FoodService fservice, int[] dataSet , double[][] batchInputData) {
		this.fservice = fservice;
		this.dataSet = dataSet;
		feedFoward = new FeedFoward();
		activationList = feedFoward.makeActivate(feedFoward.feedForwardCalc(dataSet, batchInputData));
		displayResults();
		sortResult();
	}

	public void displayResults() {
		resultList = activationList.get(activationList.size()-1);
		result = new double[2];
		double first = 0.0;	// 첫 번째 추천 음식
		double second = 0.0; // 두 번째 추천 음식
		double tmp = 0.0;
		System.out.println("분류 결과 ");
		System.out.println("***********************************");
		for (int i = 0; i < dataSet.length; i++) {
			for(int j=0 ; j<resultList[i].length ; j++) {
				if(resultList[i][j] >= 0.01) {
					if(second <= resultList[i][j]) {
						second = resultList[i][j];
						if(second >= first) {
							tmp = first;
							first = resultList[i][j];
							second = tmp;
							result[0] = j;
						} else {
							result[1] = j;
						}
					}
				}
				System.out.printf("%.2f  ",resultList[i][j]);
			}
			System.out.println();
		}
		System.out.println("***********************************");
		
		
	}

	public double[] getResult() {
		return result;
	}

	public void sortResult() {
		for (int i = 0; i < resultList[0].length; i++) {
			FoodVO vo = new FoodVO();
			vo.setIdx(i);
			vo.setRate(Double.toString(resultList[0][i]));
			try {
				fservice.update(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public double[] getRSList() {
		return resultList[0];
	}

	public void setResult(double[] result) {
		this.result = result;
	}
}
