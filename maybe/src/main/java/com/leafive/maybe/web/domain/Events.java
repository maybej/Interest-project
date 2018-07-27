package com.leafive.maybe.web.domain;

public enum Events {
	PAY(1, "支付"), RECEIVE(1, "收货");

	private Integer index;
	private String name;

	private Events(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	public static String getName(int index) {
		for (Events s : Events.values()) {
			if (s.index == index) {
				return s.name;
			}
		}
		return null;
	}
}
