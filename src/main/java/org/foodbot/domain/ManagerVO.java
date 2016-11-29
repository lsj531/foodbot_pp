package org.foodbot.domain;

import java.util.Date;

public class ManagerVO {
	private String uid;
	private String password;
	private Date credate;
	public String getUid() {
		return uid;
	}
	public void setUid(String mid) {
		this.uid = mid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCredate() {
		return credate;
	}
	public void setCredate(Date credate) {
		this.credate = credate;
	}
	
}
