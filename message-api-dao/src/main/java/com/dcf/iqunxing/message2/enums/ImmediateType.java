package com.dcf.iqunxing.message2.enums;

public enum ImmediateType {

	// 即时
	IMMEDIATE((byte) 0),
	// 定时
	SCHEDULE((byte) 1);

	private byte value;

	ImmediateType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static ImmediateType fromValue(byte value) {
		for (ImmediateType flag : ImmediateType.values()) {
			if (flag.getValue() == value) {
				return flag;
			}
		}
		return null;
	}
}
