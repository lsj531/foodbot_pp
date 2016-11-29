package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

public class MLPFunction implements ActivationFunction,GradientFunction,Nomalization{

	@Override
	public double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}

	@Override
	public double sigmoidGradient(double x) {
		return sigmoid(x) * (1 - sigmoid(x));
	}

	@Override
	public double tanh(double x) {
		return Math.tanh(x);
		//		if (x > 20)
		//			return 1.0;
		//		else if (x < -20)
		//			return -1.0;
		//		else {
		//			double a = Math.exp(x);
		//			double b = Math.exp(-x);
		//			return (a - b) / (a + b);
		//		}
	}

	@Override
	public double tanhGradient(double x) {
		return (1.0 - (tanh(x)*tanh(x)));
	}

	@Override
	public double ReLUGradient(double x) {
		if(x>0.0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public double ReLU(double x) {
		if(x > 0.0 ) {
			return x;
		} else {
			return 0;			
		}
	}

	@Override
	public double[][] setNomalization(double[][] input) throws Exception {

		double mu[] = new double[input.length];
		double sigma[] = new double[input.length];
		double[][] output = new double[input.length][input[0].length];
		double sum_tmp = 0.0;

		for(int i=0 ; i<input.length ; i++) {
			double[] tmpData = input[i];
			sum_tmp = 0.0;
			for (int j = 0; j < tmpData.length ; j++) {
				sum_tmp += tmpData[j];
			}
			mu[i] = sum_tmp / input[i].length;

			sum_tmp = 0.0;
			for (int j = 0; j < tmpData.length; j++) {
				sum_tmp += Math.pow((tmpData[j] - mu[i]), 2);
				sigma[i] = sum_tmp / (input[i].length - 1);
				sigma[i] = Math.sqrt(sigma[i]);
			}


		}
		for(int i=0 ; i<output.length ;i++) {
			for(int j=0 ; j<output[i].length ; j++) {
				output[i][j] = ((input[i][j] - mu[i]) / (input[i].length-1));
			}
		}

		return output;
	}

	@Override
	public double[] setNomalization(double[] input) throws Exception {
		
		double mu = 0.0;
		double sigma = 0.0;
		double[] output = new double[input.length];
		double sum_tmp = 0.0;

			sum_tmp = 0.0;
			for (int j = 0; j < input.length ; j++) {
				sum_tmp += input[j];
			}
			mu = sum_tmp / input.length;
			System.out.println("mu " + mu);
			sum_tmp = 0.0;
			for (int j = 0; j < input.length; j++) {
				sum_tmp += Math.pow((input[j] - mu), 2);
				sigma = sum_tmp / (input.length - 1);
				sigma = Math.sqrt(sigma);
			}
			System.out.println("sigma " + sigma);
		for(int i=0 ; i<output.length ;i++) {
				output[i] = ((input[i] - mu) / (input.length-1));
		}
		return output;
	}

	@Override
	public List<ArrayList<Double>> setNomalization(List<ArrayList<Double>> inputList) throws Exception {
		
		double[][] input = new double[inputList.size()][inputList.get(0).size()];
		
		for(int i=0 ; i<inputList.size() ; i++) {
			for(int j=0 ; j<inputList.get(i).size() ; j++) {
				input[i][j] = inputList.get(i).get(j);
			}
		}
		
		double mu[] = new double[input.length];
		double sigma[] = new double[input.length];
		List<ArrayList<Double>> outputList = new ArrayList<ArrayList<Double>>();
		double sum_tmp = 0.0;

		for(int i=0 ; i<input.length ; i++) {
			double[] tmpData = input[i];
			sum_tmp = 0.0;
			for (int j = 0; j < tmpData.length ; j++) {
				sum_tmp += tmpData[j];
			}
			mu[i] = sum_tmp / input[i].length;

			sum_tmp = 0.0;
			for (int j = 0; j < tmpData.length; j++) {
				sum_tmp += Math.pow((tmpData[j] - mu[i]), 2);
				sigma[i] = sum_tmp / (input[i].length - 1);
				sigma[i] = Math.sqrt(sigma[i]);
			}


		}

		for(int i=0 ; i<inputList.size() ; i++) {
			List<Double> tmp = new ArrayList<Double>();
			for(int j=0 ; j<inputList.get(i).size(); j++) {
				tmp.add(((input[i][j] - mu[i]) / (input[i].length-1)));
			}
			outputList.add((ArrayList<Double>) tmp);
		}

		return outputList;
	}
	
	
	

}
