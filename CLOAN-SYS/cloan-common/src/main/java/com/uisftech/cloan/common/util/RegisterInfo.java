package com.uisftech.cloan.common.util;

import java.util.Calendar;

public class RegisterInfo {

	private String key;
	private Calendar startDate;
	private Calendar endDate;
	private int disDay;
	private boolean analySuccess;

	public RegisterInfo() {

	}


	public RegisterInfo(boolean analySuccess,String key, Calendar startDate, Calendar endDate, int disDay) {
		this.analySuccess = analySuccess;
		this.key = key;
		this.startDate = startDate;
		this.endDate = endDate;
		this.disDay = disDay;
	}


	public boolean isAnalySuccess() {
		return analySuccess;
	}


	public void setAnalySuccess(boolean analySuccess) {
		this.analySuccess = analySuccess;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Calendar getStartDate() {
		return startDate;
	}


	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}


	public Calendar getEndDate() {
		return endDate;
	}


	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}


	public int getDisDay() {
		return disDay;
	}


	public void setDisDay(int disDay) {
		this.disDay = disDay;
	}

}
