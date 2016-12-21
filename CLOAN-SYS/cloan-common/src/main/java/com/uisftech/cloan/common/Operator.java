package com.uisftech.cloan.common;

public enum Operator {

	/**
	 * Equal.
	 */
	EQ("="),

	/**
	 * Not Equal.
	 */
	NE("<>"),

	/**
	 * Less Than.
	 */
	LT("<"),

	/**
	 * Less Than or Equal.
	 */
	LE("<="),

	/**
	 * Greater Than.
	 */
	GT(">"),

	/**
	 * Greater Than or Equal.
	 */
	GE(">="),

	LIKE("like"),

	LLIKE("like"),

	RLIKE("like"),

	IN("in"),

	OR("or");

	private String sign;

	private Operator(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return this.sign;
	}

}
