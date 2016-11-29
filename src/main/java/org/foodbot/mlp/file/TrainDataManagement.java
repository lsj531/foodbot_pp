package org.foodbot.mlp.file;

public interface TrainDataManagement {
	void loadWeight(String path) throws Exception;
	void SaveWeight(String path) throws Exception;
}
