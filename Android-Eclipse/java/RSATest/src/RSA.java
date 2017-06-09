import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.ArrayUtils;

import sun.misc.BASE64Decoder;

public class RSA {
	private static final String KEY_ALGORITHM = "RSA";
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;

	public RSA() {
	}

	/** 公钥加密 */
	private static String encryptByPublicKey(byte[] data, String publicKey)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyBytes = Base64.decodeBase64(publicKey);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache = null;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData != null ? Base64.encodeBase64String(encryptedData)
				: null;
	}

	/** 公钥解密 */
	public static String decodeByPubllicKey(String base64Encoded,
			String publicKey) throws Exception {
		byte[] data = (new BASE64Decoder()).decodeBuffer(base64Encoded);
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(publicKey);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i
					+ MAX_DECRYPT_BLOCK));
			sb.append(new String(doFinal));
		}
		return sb.toString();
	}

	/** 私钥解密 */
	public static String decodeByPrivateKey(String base64Encoded,
			String privateKey) throws Exception {
		byte[] data = (new BASE64Decoder()).decodeBuffer(base64Encoded);
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(privateKey);
		PKCS8EncodedKeySpec keySpec58 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		RSAPrivateKey privatekey = (RSAPrivateKey) keyFactory
				.generatePrivate(keySpec58);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privatekey);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i
					+ MAX_DECRYPT_BLOCK));
			sb.append(new String(doFinal));
		}
		return sb.toString();
	}

	/** 私钥加密 */
	private static String encryptByPrivateKey(byte[] data, String key)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] keyBytes = Base64.decodeBase64(key);

		PKCS8EncodedKeySpec keySpec58 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		RSAPrivateKey privatekey = (RSAPrivateKey) keyFactory
				.generatePrivate(keySpec58);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privatekey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache = null;
		int i = 0;
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData != null ? Base64.encodeBase64String(encryptedData)
				: null;
	}

	public static void main(String[] args) throws Exception {
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKFz3ML6RakEgRR34e19HAjrso6RPL/HfeGq9Wt2fCNHEPIWkmEPVumS2DYLV8RKWSHz38pwYnQOQWrJPiznUaUDWOcfk/YFucjAOjBf1TjkIlwT1ClLiWY2SVXGc2pBpjBluxvMob3O4thpFREIlz/tZvOIbKKKbhJo/CWuOfL9AgMBAAECgYB4yr54AdbsRTptneOiUuAOrIjCsVbKBO7hT46d/9+QZTMAbHLHuzpDNmIczQ+1BAr7pAHi64I9Ygz/X+Dsp4Qkxv9lRs2sje1xN/FVAJkQV86NWRNgLl3y2uxtCNMeOFAMhyHL1MKQ09xc5htfacBSW7qAgbuQ6LA2CXOc7jzRAQJBAOK5SgcfGnH7PGbFcuc9+p83rlsKt+yfAkgvskojrqBml37rEzkj8YH/uxCQggLa0rX34B8QnQhjU4gsTeUAIrUCQQC2TO5i/4MXo6dYe2W0f0NcRWo5w8F5UrjXVwnqqdU3VQqEP1TabESPeyYFUDGZ2VnQkvmI357m6EsLiFfsLVQpAkAkTOBiZYN5bm1MUx7DkXPYHDUpVH0AQLE/14snPbFO8z4cTw0D4BJh6w0LaOe4B0snYWmb77AE0MpmChnqqWvlAkEAjWhXcgtpEKktYgE1/zDIe4aMw4oJRabZIpCtbI2Yinc41SSbGWLqO7H26CAwmXpzpMqdLYLoq4x8ydFVAiqXIQJAWt/3p2/Bs+o3rPaDwdKHQRKWV9YOloUJdcGSx7EIMqUFMCJDto9hvWlTEVUe95jHbuCoSN0xXRpowWA9Rgq5hA==";
		String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChc9zC+kWpBIEUd+HtfRwI67KOkTy/x33hqvVrdnwjRxDyFpJhD1bpktg2C1fESlkh89/KcGJ0DkFqyT4s51GlA1jnH5P2BbnIwDowX9U45CJcE9QpS4lmNklVxnNqQaYwZbsbzKG9zuLYaRURCJc/7WbziGyiim4SaPwlrjny/QIDAQAB";
		// String data = "18451391539,100100861000010";

		String data = "{\"password\":\"827CCB0EEA8A706C4C34A16891F84E7B\"}";
		// 私钥加密
		String encryptdata = encryptByPrivateKey(data.getBytes(), privateKey);
		System.out.println("私钥加密:" + encryptdata);
		// 公钥解密
		String decodedata = decodeByPubllicKey(encryptdata, publickey);
		System.out.println("公钥解密:" + decodedata);
		// 公钥加密
		String encryptByPublicKey = encryptByPublicKey(data.getBytes(),
				publickey);
		System.out.println("公钥加密 ： " + encryptByPublicKey);
		// 私钥解密
		String decodeByPrivateKey = decodeByPrivateKey(encryptByPublicKey,privateKey);
		System.out.println("私钥解密: "+decodeByPrivateKey);
	}

}
