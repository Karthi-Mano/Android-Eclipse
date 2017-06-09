package com.example.entity;

public class HexString {
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}

	public static byte[] HexString2Bytes(String src) {
		byte[] tmp = src.getBytes();
		int len = tmp.length / 2;
		byte[] ret = new byte[len];
		for (int i = 0; i < len; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

}
