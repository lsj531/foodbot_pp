package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

import org.foodbot.time.FTime;

/*
 * 
 * 우선 추가된 자료만 학습을 해보자.
 */

public class OneLineLearning extends Learning {

	private MLPFunction mlpFunc;
	private FeedFoward feedFoward;

	// each layer's activation
	private List<double[][]> activationList = new ArrayList<double[][]>();
	// each laery's z function
	private List<double[][]> beforeActivationList = new ArrayList<double[][]>();
	// each layer's error
	private List<double[][]> deltaList = new ArrayList<double[][]>();
	// each layer's gradientdecent 
	private List<double[][]> gradientDecentList = new ArrayList<double[][]>();
	// bias
	private List<double[]> biasList = new ArrayList<double[]>();


	public static double[][] inputData;
	public static double[][] trainInputData;
	public static double[][] batchInputData;
	// training output data List
	public static int[] trainOutput;

	// random batch list
	private static int[] pattern;
	private static int[] batchNum;
	// training set num
	private static int patternNum = HyperParameter.OUTPUT_NUM;


	int[] output;


	public OneLineLearning(double[][] inputData, int[] output, boolean batchBoolean, int epoch) {
		mlpFunc = new MLPFunction();
		this.inputData = inputData;
		trainInputData = new double[inputData.length][inputData[0].length];
		trainOutput = new int[output.length];
		this.output =output;

		feedFoward = new FeedFoward();

		// 학습할 전체 패턴 갯수
		patternNum = inputData.length;

		// 추가학습은 배치가 인풋 전체  , 추가전체학습은 hyperparameter의 배치길이
		if(batchBoolean == true)
			batchNum = new int[inputData.length];
		else 
			batchNum = new int[HyperParameter.BATCH_NUM];

		initBias(patternNum);
		pattern = new int[patternNum];
		batchInputData = null;
		// 전체 output갯수 중에 추가 학습할 데이터만 값을 넣고 나머지는 0을 넣는다.
		/*
		for(int i=0 ;i<HyperParameter.OUTPUT_NUM; i++) {
			for(int j=0 ; j<output.length ; j++) {
				if(i==output[j]) {
					trainOutput[i] = 1;
					// trainInputData는 전체 비어있는 데이터이고
					//inputData는 입력된 데이터이다.
					// 입력된데이터의 원래 output의 번호에 맞는 번호로 배치된다.
					System.out.println("여기논 온라인 러닝 "+i + " "+ j + " " + trainInputData.length+ " "+output.length +" "+output[j] );
					trainInputData[i] = inputData[j];
					break;
				} else {
					trainOutput[i] = 0;
				}
			}
		}
		 */
		for(int i=0; i<inputData.length ; i++) {
			trainInputData[i] = inputData[i];
			trainOutput[i] = output[i];
		}

		FTime.startTime();
		for (int j = 0; j <= epoch; j++) { 
			setBatch();
			calcNet();

			System.out.println("epoch = " + j );
			System.out.println(j + "th cost is " + cost());
			
		}
		FTime.endTime();
		System.out.println("학습 시간은 : " + FTime.getStart2EndTime());
		displayResults();
	}

	public void displayResults() {
		setBatchData(batchNum);

		beforeActivationList = feedFoward.feedForwardCalc(batchNum,batchInputData);
		activationList = feedFoward.makeActivate(beforeActivationList);
		double[][] result = activationList.get(activationList.size()-1);
		System.out.println("***********************************");
		for (int i = 0; i < batchNum.length; i++) {
			System.out.println(i+ "번째 데이터  " );
			for(int j=0 ; j<result[i].length ; j++) {
				System.out.printf("%.2f  ",result[i][j]);
				//				System.out.print(result[i][j]+ " ");
			}
			System.out.println();
		}
		System.out.println("***********************************");
	}

	public void calcNet() {
		System.out.println("trainInputData sizse" + inputData.length);
		//		for(int i=0 ; i<batchInputData.length ; i++) {
		//			System.out.println(batchInputData.length);
		//			for(int j=0 ; j<batchInputData[i].length;j++){
		//				System.out.println(batchInputData[i].length);
		//			System.out.print(batchInputData[i][j]+" ");
		//			}
		//		}
		beforeActivationList = feedFoward.feedForwardCalc(batchNum,batchInputData);
		activationList = feedFoward.makeActivate(beforeActivationList);
		deltaList = bpCalc(activationList,beforeActivationList,batchNum,output);
		gradientDecentList = gradientDecent(deltaList,activationList);
		for(int i=0 ; i<HyperParameter.weights.size() ; i++) {
			//			System.out.println(i + " 번째 wieght "
			//						+ HyperParameter.weights.get(i).length + " " +HyperParameter.weights.get(i)[0].length 
			//						+ " " + HyperParameter.weights.get(i)[0][0]);
			for(int j=0 ; j<HyperParameter.weights.get(i).length ; j++) {
				//				System.out.println(j+ " 번째 레이어");
				for(int k=0 ; k<HyperParameter.weights.get(i)[j].length ; k++) {
					//					System.out.print(HyperParameter.weights.get(i)[j][k] + " ");
				}
				//				System.out.println();
			}
			//System.out.println();
		}
		/*
		for(int i=0 ; i<activationList.size() ;i++) {
			System.out.println("beforeActivationList "+" "+beforeActivationList.get(i).length + " "+beforeActivationList.get(i)[0].length);
		}
		for(int i=0 ; i<activationList.size() ;i++) {
			System.out.println("activationList "+" "+activationList.get(i).length + " "+activationList.get(i)[0].length);
		}
		for(int i=0 ; i<deltaList.size() ;i++) {
			System.out.println("deltaList "+" "+deltaList.get(i).length + " "+deltaList.get(i)[0].length);
		}
		for(int i=0 ; i<gradientDecentList.size() ;i++) {
			System.out.println("gradientDecentList "+" "+gradientDecentList.get(i).length + " "+gradientDecentList.get(i)[0].length);
		}
		*/
		//		for(int i =0 ;i<gradientDecentList.size() ; i++) {
		//			System.out.println("gradientDecentList.size() "+gradientDecentList.size()+" "+ gradientDecentList.get(i).length +
		//									 "  " + gradientDecentList.get(i)[0].length);
		//			
		//				
		//		}
		learning();
		//		saveBias();
	}

	private void saveBias() {
		for(int i=0 ; i<patternNum ; i++) {
			for(int j=0; j<batchNum.length ; j++) {
				if(batchNum[j] == i) {
					for(int k=0 ; k<activationList.size() ; k++) {
						HyperParameter.biasList.get(k)[batchNum[j]] = activationList.get(k)[j][0];
						System.out.println(k+"레이어 "+batchNum[j]+"번째 set  activationList.get(k)[j][0] " +activationList.get(k)[j][0]);
					}
				}
			}
		}
	}

	private void learning() {
		for(int i=0 ; i<HyperParameter.weights.size() ; i++) {
			for(int j=0 ; j<HyperParameter.weights.get(i).length ; j++) {
				for(int k=0 ; k<HyperParameter.weights.get(i)[j].length ; k++) {
					HyperParameter.weights.get(i)[j][k] 
							= HyperParameter.weights.get(i)[j][k] 
									- (HyperParameter.LEARNING_RATE * gradientDecentList.get(HyperParameter.weights.size()-i-1)[j][k]);
				}
			}
		}
	}



	// trainset을 batch_num 만큼 랜덤set 으로 가져온다.
	private void setBatch() {
		//초기화
		for(int i=0 ; i<batchNum.length;i++) {
			batchNum[i] = -2;
		}
		int tmp = -1;
		boolean flag = false;
		for(int i=0 ;i<batchNum.length ; i++) {
			tmp = (int) ((Math.random() * patternNum) - 0.001);
			for(int j=0; j <= i; j++) {
				if(tmp==batchNum[j]) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if(flag == true)  {
				batchNum[i] = tmp;
			} else {
				i--;
			}
		}
		for(int i=0 ; i<pattern.length ; i++) {
			pattern[i] = i;
		}
		/*
		System.out.println("Batch!!" + batchNum.length);
		for(int i=0 ; i<batchNum.length ; i++) {
			System.out.print(batchNum[i] + "  ");
		}
		*/
		setBatchData(batchNum);
	}

	private void setBatchData(int[] batch) {
		batchInputData = new double[batch.length][HyperParameter.INPUT_NUM];
		for(int i=0 ; i<batch.length ; i++) {
			batchInputData[i] = trainInputData[batch[i]];
		}
	}

	// cost는 전체 dataset 에 대한 정보이다.
	public double cost() {
		System.out.println("pattercost "+ pattern.length);
		setBatchData(pattern);
		List<double[][]> actList = feedFoward.makeActivate(feedFoward.feedForwardCalc(pattern,batchInputData));
		double[][] hypo = actList.get(actList.size()-1);

		// cost
		double J = 0.0;
		/*
		 * cost operation
		 */
		for(int i=0 ;i<patternNum ; i++) {
			double htheta[] = new double[HyperParameter.OUTPUT_NUM];
			double yi[] = new double[HyperParameter.OUTPUT_NUM];
			double jk = 0.0;
			/*
			 * 클래스 label과 dataset label
			 * 값이 같으면 1 다르면 0으로 된  벡터를 생성한다
			 */
			for(int j=0 ;j<HyperParameter.OUTPUT_NUM ; j++) { 
				if(i==j)
					yi[j] = 1;
				else 
					yi[j] = 0;
			}
			/*
			 * 현재 연산중인 class에 대한 attribute value 를 가져온다
			 */
			for(int k=0 ; k<hypo[i].length ; k++) {
				htheta[k] = hypo[i][k];
				//				System.out.println("htheta"+htheta[k] );
			}
			/*
			 * 각 class 별로 cost를 구한 후 모두 더한다
			 */
			jk = (1.0/patternNum)*sum(yi,htheta);
			J = J + jk;
		}
		return J;
	}

	private double sum(double[] yi, double[] htheta) {
		double sum=0.0;
		//	for(int i=0 ; i<yi.length; i++) {	
		for(int j=0 ; j<htheta.length ; j++) {
			if(yi[j] == 0) {
				if(htheta[j] >= 1.0) {
					htheta[j] = 0.999;
				}
				sum = sum + (-((1.0-yi[j]) * (double)Math.log(1.0-htheta[j])));

			} else {
				if(htheta[j] <= 0.0) {
					htheta[j] = 0.0001;
				}
				sum = sum+ (-yi[j] * (double)Math.log(htheta[j]));
			}
			//					sum += ((-yi[i] * (double)Math.log(htheta[j])) - ((1.0-yi[i]) * (doubles)Math.log(1.0-htheta[j])));
			//					System.out.println(j+ " htheta[j]) "+htheta[j]);
		}
		//				System.out.println("sum "+sum);
		//}
		return sum;
	}
}
