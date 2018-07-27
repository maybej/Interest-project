package com.leafive.maybe.web.domain;

public enum States {
	UNPAID(1, "未支付"), WAITING_FOR_RECEIVE(1, "待收货	"), DONE(1, "结束");

	private Integer index;
	private String name;

	private States(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	public static String getName(int index) {
		for (States s : States.values()) {
			if (s.index == index) {
				return s.name;
			}
		}
		return null;
	}
}
