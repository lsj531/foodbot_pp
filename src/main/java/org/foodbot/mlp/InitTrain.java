package org.foodbot.mlp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.foodbot.service.FoodService;
import org.foodbot.time.FTime;

public class InitTrain extends Learning {
	
	private MLPFunction mlpFunc;
	private FeedFoward feedFoward;
	@Inject 
	private FoodService fservice;
	
	private double cost;

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
	// training input data List
	private List<ArrayList<Double>> trainInputList;
	private static double[][] trainInputData;
	private static double[][] batchInputData;
	// training output data List	
	public static int[] trainOutput;

	// random batch list
	private static int[] pattern;
	private static int[] batchNum;
	// training set num
	private static int patternNum;


	public InitTrain(FoodService fservice) throws Exception {
		this.fservice = fservice;

		// mlp function instance ..
		mlpFunc = new MLPFunction();
		feedFoward = new FeedFoward();

		// input data
		InitTrainData itd = new InitTrainData(fservice);
		trainInputList = itd.getTrainDataList();
		trainOutput = itd.getTrainOuputData();
		
//		for(int i=0 ; i<trainDataList.size() ; i++) {
//			System.out.print(i+" 번째 : ");
//			for(int j=0 ;j<trainDataList.get(i).size() ; j++) {
//				System.out.print(trainDataList.get(i).get(j) + " ");
//			}System.out.println();
//		}

		initWeights();
		patternNum = trainInputList.size();
		initBias(patternNum);
		batchNum = new int[HyperParameter.BATCH_NUM];
		pattern = new int[patternNum];
		trainInputData = new double[patternNum][HyperParameter.INPUT_NUM];
		batchInputData = null;

	
		for(int i=0  ; i<patternNum ; i++) {
			for(int j=0 ; j<trainInputList.get(i).size() ; j++) {
				trainInputData[i][j] = trainInputList.get(i).get(j);
			}
		}
//		System.out.println("trainInputDatatrainInputData" +trainInputData.length + " "+trainInputData[0].length);
//		// init weight
//		for(int i=0 ; i<trainInputData.length ; i++) {
//			System.out.print(i+" ");
//			for(int j=0 ; j<trainInputData[i].length ; j++) {
//				System.out.print(trainInputData[i][j] + " ");
//			}
//			System.out.println();
//		}

		/*
		FTime.startTime();
		for (int j = 0; j <= HyperParameter.EPOCH; j++) {
			setBatch();
			calcNet();
			
			System.out.println("epoch = " + j );
			cost = cost();
			System.out.println(j + "th cost is "+cost());
		}
		cost = 0;
		
		FTime.endTime();
		System.out.println("학습 시간은 : " + FTime.getStart2EndTime());
		displayResults();
		*/
	}
	public void displayResults() {
		setBatchData(pattern);
		beforeActivationList = feedFoward.feedForwardCalc(pattern,batchInputData);
		activationList = feedFoward.makeActivate(beforeActivationList);
		double[][] result = activationList.get(activationList.size()-1);
		System.out.println("***********************************");
		for (int i = 0; i < patternNum; i++) {
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
		beforeActivationList = feedFoward.feedForwardCalc(batchNum,batchInputData);
		activationList = feedFoward.makeActivate(beforeActivationList);
		deltaList = bpCalc(activationList,beforeActivationList,batchNum,trainOutput);
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

	public void saveBias() {
		for(int i=0 ; i<patternNum ; i++) {
			for(int j=0; j<batchNum.length ; j++) {
				if(batchNum[j] == i) {
					for(int k=0 ; k<activationList.size() ; k++) {
						HyperParameter.biasList.get(k)[batchNum[j]] = activationList.get(k)[j][0];
//						System.out.println(k+"레이어 "+batchNum[j]+"번째 set  activationList.get(k)[j][0] " +activationList.get(k)[j][0]);
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
	public void setBatch() {
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
//					System.out.println("여기는 배치 " + tmp+ " "+trainOutput[tmp]);
					batchNum[i] = tmp;
				} else {
					i--;
				}
			}
			/*
			System.out.println("Batch!!" + batchNum.length);
			for(int i=0 ; i<batchNum.length ; i++) {
				System.out.print(batchNum[i] + "  ");
			}
			*/
			// 1~ input data num 
			for(int i=0 ; i<pattern.length ; i++) {
				pattern[i] = i;
			}
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
//				sum += ((-yi[i] * (double)Math.log(htheta[j])) - ((1.0-yi[i]) * (doubles)Math.log(1.0-htheta[j])));
//				System.out.println(j+ " htheta[j]) "+htheta[j]);
			}
//			System.out.println("sum "+sum);
		//}
		return sum;
	}
	public static double[][] getTrainInputData() {
		return trainInputData;
	}

	public static int[] getTrainOutput() {
		return trainOutput;
	}
	public double getCost() {
		return cost;
	}

}
