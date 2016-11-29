package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

public class FeedFoward extends Init {

	MLPFunction mlpFunc = new MLPFunction();
	public FeedFoward() {}
	
	/*
	 *  2~output layer 까지의 activation List 를 구한다.
	 */
	public List<double[][]> feedForwardCalc(int[] dataSet, double[][] batchInputData) {
		List<double[][]> actList = new ArrayList<double[][]>();
		// a1 insert ( input data )
		actList.add(addBiasToActivation(batchInputData,0));

		// feed forward calc
		// activation : sigmoid
		for(int i=0 ; i<HyperParameter.weights.size() ; i++) {
			double[][] tmp =  new double[dataSet.length][HyperParameter.weights.get(i)[0].length];
			// hidden node 의 activation 함수에 bias 를 추가한다.
			if(i<HyperParameter.weights.size()-1)
				tmp = addBiasToActivation(tmp,i+1);
			for(int n=0; n<dataSet.length ; n++) {
				for(int j=0 ;j<HyperParameter.weights.get(i)[0].length; j++) {
					for(int k=0 ; k<HyperParameter.weights.get(i).length; k++) {
//						System.out.println("n "+n + " k " +k+" i " + i );
						tmp[n][j] = tmp[n][j]  + actList.get(i)[n][k] * HyperParameter.weights.get(i)[k][j];
					}
				}
			}
			actList.add(tmp);
		}
		//		System.out.println("all activation size " + actList.size());
		return actList;
	}
	
	// 최초 feedfoward에 hidden node's activation에 bias를 추가한다.
		// 두번째부턴 생각을 ..
		private double[][] addBiasToActivation(double[][] act, int layer) {
			double[][] tmp = new double[act.length][act[0].length+1];

			for(int i=0; i<act.length ; i++) {
				for(int j=0 ; j<act[i].length ; j++) {
					if(j==0) {
						tmp[i][0] = HyperParameter.biasList.get(layer)[i];
					}
					else
						tmp[i][j+1] = act[i][j];
				}
			}
			return tmp;
		}
		List<double[][]> makeActivate(List<double[][]> tmp) {
			// input layer를 제외한 모든 layer activate
			for(int i=1 ; i<tmp.size() ; i++) {
				for(int j=0; j<tmp.get(i).length ; j++){
					for(int k=0 ; k<tmp.get(i)[j].length ; k++) {
						tmp.get(i)[j][k] = mlpFunc.ReLU(tmp.get(i)[j][k]);
					}
				}
			}
			return tmp;
		}

}
