package org.foodbot.domain;

import java.util.Date;

public class MLPWeightVO {
	private String uid;
	private String attribute_path;
	private String tattribute_path;
	private String weight_path;
	private String output_path;
	private String toutput_path;
	private String cost;
	private Date credate;
	private Date moddate;
	private int learn_b;
	private int learn_curr;
	
	public int getLearn_curr() {
		return learn_curr;
	}
	public void setLearn_curr(int learn_curr) {
		this.learn_curr = learn_curr;
	}
	public String getToutput_path() {
		return toutput_path;
	}
	public void setToutput_path(String toutput_path) {
		this.toutput_path = toutput_path;
	}
	public String getOutput_path() {
		return output_path;
	}
	public void setOutput_path(String output_path) {
		this.output_path = output_path;
	}
	public String getTattribute_path() {
		return tattribute_path;
	}
	public void setTattribute_path(String tattribute_path) {
		this.tattribute_path = tattribute_path;
	}
	public int getLearn_b() {
		return learn_b;
	}
	public void setLearn_b(int learn_b) {
		this.learn_b = learn_b;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAttribute_path() {
		return attribute_path;
	}
	public void setAttribute_path(String attribute_path) {
		this.attribute_path = attribute_path;
	}
	public String getWeight_path() {
		return weight_path;
	}
	public void setWeight_path(String weight_path) {
		this.weight_path = weight_path;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	public Date getModdate() {
		return moddate;
	}
	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}

}
