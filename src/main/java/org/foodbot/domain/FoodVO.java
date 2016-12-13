package org.foodbot.domain;

import java.util.Date;

// 음식명과 재료 코드 vo
public class FoodVO {

	private String fcode;
	private String fname;
	private String ingredset;
	private String tasteset;
	private String image_path;
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	private String nng;
	private String nng_code;
	
	public String getTasteset() {
		return tasteset;
	}
	public void setTasteset(String tasteset) {
		this.tasteset = tasteset;
	}
	public String getIngredset() {
		return ingredset;
	}
	public void setIngredset(String ingredset) {
		this.ingredset = ingredset;
	}
	public String getNng() {
		return nng;
	}
	public void setNng(String nng) {
		this.nng = nng;
	}
	public String getNng_code() {
		return nng_code;
	}
	public void setNng_code(String nng_code) {
		this.nng_code = nng_code;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	private String rate;
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	private int idx;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

}
