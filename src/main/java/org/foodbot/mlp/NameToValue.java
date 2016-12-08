package org.foodbot.mlp;

import java.util.List;

import org.foodbot.service.FoodService;

/*
 * 음식명이나 속셩명을  레이블 및 수치화 한다.
 */
public class NameToValue {

	private FoodService fservice;
	private List<String> dataName;
	private List<String> foodName;
	
	public NameToValue(FoodService fservice) throws Exception {
		this.fservice = fservice;
		
		TrainDataName trainData = new TrainDataName(fservice);
		dataName = trainData.getDataName();
		foodName = trainData.getOutputName();
	}
	
	// 음식명(outputList)을 output 레이블로 바꾸어준다
	public double[] outputToValue(List<String> outputList) {
		double[] output = new double[outputList.size()];
		for(int i=0 ; i<foodName.size() ; i++) {
			for(int j=0 ; j<outputList.size() ; j++) {
				if(foodName.get(i).equals(outputList.get(j))) {
					output[j] = i;
				}
			}
		}
		return output;
	}
	// recipe의 음식명(outputList)을 output 레이블로 바꾸어준다
	public double[] rOutputToValue() {
		double[] output = new double[foodName.size()];
		for(int i=0 ; i<foodName.size() ; i++) {
					output[i] = i;
		}
		return output;
	}
}
