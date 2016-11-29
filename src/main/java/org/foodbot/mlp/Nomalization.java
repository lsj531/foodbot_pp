package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

public interface Nomalization {
	public double[][] setNomalization(double[][] input) throws Exception;
	public double[] setNomalization(double[] input) throws Exception;
	public List<ArrayList<Double>> setNomalization(List<ArrayList<Double>> input) throws Exception;
}
