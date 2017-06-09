/*
 * @(#)DESEncrypt.java
 * 2012-7-1
 * Copyright 2012 Win MicroSystems, Inc. All rights reserved.
 * WIN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.example.level3encrypt.util;

import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密解密
 * 
 * @author YUY
 * @version [1.0, 2013-10-26]
 * @see [相关类#方法(参数)]
 * @since [产品/模块版本]
 */
public class DESEncrypt {
	public static final String DEFAULT_KEY = "WIN1314";
	// 编码格式
	private static final String CHARSET = "GBK";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * 字节数组转换为字符串
	 * 
	 * @param arrB
	 * @return
	 * @return String
	 * @remark create WuBin 2012-7-4
	 */
	private String byteArrToHexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte是十六进制两个字符才能表示,所以字符长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTemp = arrB[i];
			// 把负数转换为正数
			if (intTemp < 0) {
				intTemp = intTemp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTemp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTemp, 16));
		}
		return sb.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 * @return byte[]
	 * @remark create WuBin 2012-7-4
	 */
	private byte[] hexStrToByteArr(String strIn) throws Exception {
		// strIn不存在汉字，不需要对编码格式进行特殊指定
		byte[] arrB = strIn.getBytes(CHARSET);
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrout = new byte[iLen / 2];
		String strTmp = "";
		for (int i = 0; i < iLen; i = i + 2) {
			strTmp = new String(arrB, i, 2, CHARSET);
			arrout[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrout;
	}

	/**
	 * 构造函数 Description:
	 */
	public DESEncrypt() throws Exception {
		this(DEFAULT_KEY);
	}

	public DESEncrypt(String strkey) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(strkey.getBytes(CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, sr);
		decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
		decryptCipher.init(Cipher.DECRYPT_MODE, key, sr);
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 * @return
	 * @throws Exception
	 * @return byte[]
	 * @remark create WuBin 2012-7-4
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		// 如果被加密字节传位数不是8的倍数，用00补齐
		if (arrB.length % 8 != 0) {
			byte[] arrTmp = new byte[arrB.length + 8 - arrB.length % 8];
			System.arraycopy(arrB, 0, arrTmp, 0, arrB.length);
			arrB = arrTmp;
		}
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @remark create WuBin 2012-7-4
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArrToHexStr(encrypt(strIn.getBytes(CHARSET)));
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 * @return
	 * @throws Exception
	 * @return byte[]
	 * @remark create WuBin 2012-7-4
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * @param strIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @remark create WuBin 2012-7-4
	 */
	public String decrypt(String strIn) throws Exception {
		byte[] decByte = decrypt(hexStrToByteArr(strIn));
		// 如果被加密字符串传位数不是8的倍数，会用00补齐，解密后去除补的00
		for (int i = 0; i < decByte.length; i++) {
			if (decByte[i] == 0) {
				byte[] tmpByte = new byte[i];
				System.arraycopy(decByte, 0, tmpByte, 0, i);
				decByte = tmpByte;
				break;
			}
		}
		return new String(decByte, CHARSET);
	}
}