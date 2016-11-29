package org.foodbot.mlp;

import java.util.List;

/*
 * 선행조건 : weight 초기화
 * 
 * feedFoward를 통해
 * 결과 음식을 출력시켜준다.
 */
public class Classification {
	private FeedFoward feedFoward;
	private List<double[][]> activationList;
	private int[] dataSet;
	private double[] result;
	public Classification(int[] dataSet , double[][] batchInputData) {
		this.dataSet = dataSet;
		feedFoward = new FeedFoward();
		activationList = feedFoward.makeActivate(feedFoward.feedForwardCalc(dataSet, batchInputData));
		displayResults();	
	}

	public void displayResults() {
		double[][] resultList = activationList.get(activationList.size()-1);
		result = new double[2];
		double first = 0.0;
		double second = 0.0;
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

	public void setResult(double[] result) {
		this.result = result;
	}

}
