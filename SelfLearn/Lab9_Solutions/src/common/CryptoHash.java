package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHash {
	
	final static String HASH_SHA3_256 = "SHA3-256";
	
	public static byte[] genSha3Digest256Multi(String msg, int round) {
		byte[] input = AesEncryption.getUTF8Bytes(msg);
		MessageDigest sha3;
		byte[] hash256 = new byte[32];
		try {
			sha3 = MessageDigest.getInstance(HASH_SHA3_256);
			for(int i=0; i<round-1; i++) {
				sha3.update(input);
			}
			
			hash256 = sha3.digest();
			
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR|| SHA3-256 NoSuchAlgorithmException.");
			e.printStackTrace();
		}
		
		
		return hash256;
	}
	
	public static byte[] genSha3Digest256(String msg) {
		byte[] input = AesEncryption.getUTF8Bytes(msg);
		MessageDigest sha3;
		byte[] hash256 = new byte[32];
		try {
			sha3 = MessageDigest.getInstance(HASH_SHA3_256);
			sha3.update(input);
			hash256 = sha3.digest();
			
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR|| SHA3-256 NoSuchAlgorithmException.");
			e.printStackTrace();
		}
		
		
		return hash256;
	}

}
