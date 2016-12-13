package org.foodbot.mlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.foodbot.config.Config;
import org.foodbot.mlp.file.TrainDataManagement;
import org.foodbot.time.FTime;

/*
 *  initweight / initbias / data save & load
 */
public class Init implements InitMLP, TrainDataManagement {

	private static final String COL = "col";
	private static final String LAYER = "layer";
	private static final String T = "\t";

	public Init() {}

	// Initialize the values of weight
	@Override
	public void initWeights() {
		for (int i = 0; i < HyperParameter.weights.size(); i++) {
			for (int j = 0; j < HyperParameter.weights.get(i).length; j++) {
				for (int k = 0; k < HyperParameter.weights.get(i)[j].length; k++) {
					HyperParameter.weights.get(i)[j][k] = (Math.random() - 0.5) / 10;
				}
			}
		}
	}

	// Initialize the values of bias
	@Override
	public void initBias(int patternNum) {
		HyperParameter.biasList = null;
		HyperParameter.biasList = new ArrayList<double[]>();

		for (int k = 0; k < HyperParameter.TOTAL_LAYER_NUM; k++) {
			HyperParameter.biasList.add(new double[patternNum]);
		}
		for (int j = 0; j < patternNum; j++) {
			for (int k = 0; k < HyperParameter.TOTAL_LAYER_NUM; k++) {
				HyperParameter.biasList.get(k)[j] = 1;
			}
		}
		System.out.println("bias 초기화 완료");
	}

	@Override
	public void loadWeight(String path) throws Exception {
		String w = "";
		FTime.startTime();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String ss = "";
		String s = "";
		do {
			s = br.readLine();
			ss = ss + s;
		} while (!(s == null));

		List<double[][]> gweight = HyperParameter.weights;
		String[] l = ss.split(LAYER);
		// System.out.println("l :"+ l.length);
		for (int i = 0; i < l.length - 1; i++) {
			String[] c = l[i].split(COL);
			for (int j = 0; j < c.length; j++) {
				String[] in = c[j].split(T);
				for (int k = 0; k < in.length; k++) {
					gweight.get(i)[j][k] = Double.parseDouble(in[k]);
				}
			}
		}
		// load to Hyper weight
		HyperParameter.weights = gweight;
		FTime.endTime();
		System.out.println("weight load 걸린 시간 : " + FTime.getStart2EndTime());
		br.close();
		fr.close();
	}

	@Override
	public void SaveWeight(String path) throws Exception {
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);

		String w = "";
		List<double[][]> weight = HyperParameter.weights;
		FTime.startTime();
		for (int i = 0; i < weight.size(); i++) {
			double[][] row = weight.get(i);
			for (int j = 0; j < row.length; j++) {
				double[] col = row[j];
				for (int k = 0; k < col.length; k++) {
					bw.append(w + col[k] + T);
					// w = w +col[k] + T;
				}
				bw.append(COL);
				// w = w + COL;
			}
			bw.append(LAYER);
			// w = w + LAYER;
		}
		// bw.write(w);
		bw.flush();
		bw.close();
		fw.close();
		System.out.println("학습 데이터 저장 완료");
		FTime.endTime();
		System.out.println("걸린 시간 " + FTime.getStart2EndTime());
	}

	public void saveTAttribute(String path, double[] attr) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);

		String w = "";
		for (int i = 0; i < attr.length; i++) {
			bw.append(w + attr[i] + T);
		}
		bw.append(COL);

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("tattribute 데이터 저장 완료");
	}

	public void saveTAttribute(String path, double[][] attr) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);

		String w = "";
		for (int i = 0; i < attr.length; i++) {
			for (int j = 0; j < attr[i].length; j++) {
				bw.append(attr[i][j] + T);

			}
			bw.append(COL);
		}

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("tattribute 데이터 저장 완료");
	}

	public double[][] loadTAttribute(String path) throws Exception {
		String w = "";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String ss = "";
		String s = "";
		do {
			s = br.readLine();
			ss = ss + s;
		} while (!(s == null));

		String[] col = ss.split(COL);
		String[] tmp = col[0].split(T);
		double[][] attr = new double[col.length - 1][tmp.length];
		for (int i = 0; i < col.length - 1; i++) {
			String[] in = col[i].split(T);
			for (int j = 0; j < in.length; j++) {
				attr[i][j] = Double.parseDouble(in[j]);
			}
		}
		System.out.println("tattribute 로드 완료");
		br.close();
		fr.close();

		return attr;
	}

	public void deleteTAttribute(String path) throws Exception {
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.append("");

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("tattribute 데이터 초기화 완료");
	}

	public void saveTOutput(String path, int output) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.append(output + T);

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("toutput 데이터 저장 완료");
	}

	public void saveTOutput(String path, int[] output) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < output.length; i++) {
			System.out.print(output[i] + " ");
			bw.append(output[i] + T);
		}

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("toutput 데이터 저장 완료");
	}

	public int[] loadTOutput(String path) throws Exception {
		String w = "";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String ss = "";
		String s = "";
		do {
			s = br.readLine();
			ss = ss + s;
		} while (!(s == null));
		String[] attr = ss.split(T);
		int[] output = new int[attr.length - 1];
		for (int i = 0; i < output.length; i++) {
			output[i] = Integer.parseInt(attr[i]);
		}

		System.out.println("toutput 로드 완료");
		br.close();
		fr.close();

		return output;
	}

	public void deleteTOutput(String path) throws Exception {
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.append("");

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("toutput 데이터 초기화 완료");
	}

	public void saveAttribute(String path, double[][] attr) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < attr.length; i++) {
			for (int j = 0; j < attr[i].length; j++) {
				bw.append(attr[i][j] + T);
			}
			bw.append(COL);
		}

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("attribute 데이터 저장 완료");
	}

	public double[][] loadAttribute(String path) throws Exception {
		String w = "";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String ss = "";
		String s = "";
		do {
			s = br.readLine();
			ss = ss + s;
		} while (!(s == null));

		String[] col = ss.split(COL);
		String[] tmp = col[0].split(T);
		double[][] attr = new double[col.length - 1][tmp.length];
		for (int i = 0; i < col.length - 1; i++) {
			String[] in = col[i].split(T);
			for (int j = 0; j < in.length; j++) {
				attr[i][j] = Double.parseDouble(in[j]);
			}
		}
		System.out.println("attribute 로드 완료");
		br.close();
		fr.close();

		return attr;
	}

	public void saveOutput(String path, int[] output) throws Exception {
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < output.length; i++) {
			bw.append(output[i] + T);
		}

		bw.flush();
		bw.close();
		fw.close();
		System.out.println("output 데이터 저장 완료");
	}

	public int[] loadOutput(String path) throws Exception {
		String w = "";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String ss = "";
		String s = "";
		do {
			s = br.readLine();
			ss = ss + s;
		} while (!(s == null));
		String[] attr = ss.split(T);
		int[] output = new int[attr.length - 1];
		for (int i = 0; i < output.length; i++) {
			output[i] = Integer.parseInt(attr[i]);
		}

		System.out.println("output 로드 완료");
		br.close();
		fr.close();

		return output;
	}
}
