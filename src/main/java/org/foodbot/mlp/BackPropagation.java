package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

public class BackPropagation extends FeedFoward {

	MLPFunction mlpFunc = new MLPFunction();


	public BackPropagation(){
	}

	protected List<double[][]> bpCalc(List<double[][]> activationList
			,List<double[][]> beforeActivationList
			,int[] batchNum, int[] trainOutput) {
		List<double[][]> tmpDeltaList = new ArrayList<double[][]>();
		double[][] tmpDelta;
		for(int i=HyperParameter.BP_NUM-1 ; i>=0; i--) {
			switch(i) {
			// output layer's delta (error)
			case HyperParameter.BP_NUM-1 :
				double[][] lastAct = activationList.get(activationList.size()-1);
				tmpDelta = lastAct;
				//				System.out.println("lastAct data size " + lastAct.length);
				// batch set
				for(int j=0 ; j<batchNum.length ; j++) {
					// each training set의 output label과 
					// 모든 output label 을 비교하여 맞는 index의 activation 값만 -1을 취한다.
					for(int k = 0; k<HyperParameter.OUTPUT_NUM; k++) {
						// 실제 데이터 배열에 인덱스인 batchNum 을 넣는다 
						if(trainOutput[batchNum[j]] == k) {
							tmpDelta[j][k] = lastAct[j][k]-1;
						}
					}
				}
				tmpDeltaList.add(tmpDelta);
				//				System.out.println("deltaList 1 : " +tmpDeltaList.get(0).length);
				//				System.out.println("weight size "+HyperParameter.weights.size());
				break;
				// hidden layer's delta (error)
			default :
				double[][] beforeAct = beforeActivationList.get(i+1);
				tmpDelta = new double[beforeAct.length][beforeAct[0].length];
				// batch
				for(int j=0 ; j<batchNum.length ; j++) {
					double[][] tmpWeight = HyperParameter.weights.get(i+1);
					// weight 입력단
					for(int n=0; n<tmpWeight.length ; n++) {
						// weight 출력단
						for(int m=0 ; m<tmpWeight[n].length ; m++) {
							//System.out.println(j + " " + " " + m + " " + n + " " + tmpWeight[n].length);
							tmpDelta[j][m] = tmpWeight[n][m] * tmpDeltaList.get(tmpDeltaList.size()-1)[j][m] * mlpFunc.ReLUGradient(beforeAct[j][m]);
						}
					}
				}
				tmpDeltaList.add(tmpDelta);
				//				System.out.println("tmpDelta " + tmpDelta.length);
				//				delta_2 = Theta2' * delta_3' .* sigmoidGradient([1, z2(t, :)])';
				//						delta_2 = delta_2(2:end);
				break;
			}
		}
		for(int i=0 ; i<tmpDeltaList.size() ; i++) {
			//			System.out.println("deltaList "+ i + " " + tmpDeltaList.get(i).length + " " + tmpDeltaList.get(i)[0].length);
		}
		return tmpDeltaList;
	}

	protected List<double[][]> gradientDecent(List<double[][]> delta,List<double[][]> activationList) {
		List<double[][]> tmpList = new ArrayList<double[][]>();
		List<double[][]> tmpGradientList = new ArrayList<double[][]>();
		double[][] tmpGradient = null;
		double[][] tmp = null;
		for(int d=0 ; d<delta.size() ; d++) {
			if(d != 0)
				tmp = new double[delta.get(d).length][delta.get(d)[0].length-1];
			else 
				tmp = new double[delta.get(d).length][delta.get(d)[0].length];
			for(int i=0 ; i<tmp.length ; i++) {
				for(int j=0 ;j<tmp[i].length ; j++) {
					if(d != 0)
						tmp[i][j] = delta.get(d)[i][j+1];
					else 
						tmp[i][j] = delta.get(d)[i][j];
				}
			}
			tmpList.add(tmp);
		}
		for(int i=0 ; i<tmpList.size() ; i++) {	
			tmpGradient = new double[activationList.get(tmpList.size()-i-1)[0].length][tmpList.get(i)[0].length];
			for(int j=0 ; j<tmpList.get(i).length ; j++) {
				for(int l=0 ; l<activationList.get(tmpList.size()-i-1)[j].length ; l++) {
					for(int k=0 ; k<tmpList.get(i)[j].length ; k++) {
						tmpGradient[l][k] =  activationList.get(tmpList.size()-i-1)[j][l] * tmpList.get(i)[j][k];
					}
				}
			}
			tmpGradientList.add(tmpGradient);
		}
		return tmpGradientList;
	}
}
