package test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import common.AesEncryption;
import common.CryptoHash;
import common.CryptoRandomGenerator;

public class MutualAuthSKImprovedSec {

	public static void main(String[] args) {
		// use your password as the AES key trigger - 256 bits
		String passwd = "s3703131";
		// generate digest of shared password - 256 bits
		byte[] secretTrigger1 = CryptoHash.genSha3Digest256Multi(passwd, 1);
		byte[] secretTrigger2 = CryptoHash.genSha3Digest256Multi(passwd, 2);
		System.out.println("[Shared password]:" + passwd);
		System.out.println("[Digest1 of shared password]:" + Base64.encodeBase64String(secretTrigger1));
		System.out.println("[Digest2 of shared password]:" + Base64.encodeBase64String(secretTrigger2));
		String ivTrigger = "s3703131s3703131";
		

		try {
			// Alice sends id
			System.out.println("[Alice side]: Alice sends id to Bob.");

			// Bob generates challenge R1 and sends to Alice
			CryptoRandomGenerator ranGen = new CryptoRandomGenerator();
			int length = 32; // 32 bytes = 256 bits
			byte[] r1 = ranGen.genCryptoRanOdd(length);
			String r1Base64 = Base64.encodeBase64String(r1);
			System.out.println("[Bob side]: Bob generates challenge odd number R1 randomly. R1:" + r1Base64);
			System.out.println("ByteArray of R1:"+Arrays.toString(r1));

			// Alice generates R2
			byte[] r2 = ranGen.genCryptoRanEven(length);
			String r2Base64 = Base64.encodeBase64String(r2);
			System.out.println("[Alice side]: Alice generates challenge even number R2 randomly. R2:" + r2Base64);
			System.out.println("ByteArray of R2:"+Arrays.toString(r2));
			// Alice side AES_K1(R1)
			AesEncryption aesEnc = new AesEncryption(secretTrigger1, ivTrigger);
			byte[] r1Cipher = aesEnc.encAes256CbcByte(r1);
			String r1CipherBase64 = Base64.encodeBase64String(r1Cipher);
			System.out.println("[Alice side]: Alice sends encrypted Enc(R1) and R2 to Bob: " + r1CipherBase64);

			// Bob decrypts DEc(R1) to verify
			System.out.println("[Bob side]: Bob decrypts and gets R1 to verify the correctness.");

			// Bob eNcrypts R2 AES_K2(R2)
			AesEncryption aesEnc2 = new AesEncryption(secretTrigger2, ivTrigger);
			byte[] r2Cipher = aesEnc2.encAes256CbcByte(r2);
			String r2CipherBase64 = Base64.encodeBase64String(r2Cipher);
			System.out.println("[Bob side]: Bob sends encrypted Enc(R2) and R1 to Alice: " + r2CipherBase64);

			System.out.println("[Alice side]: Alice decrypts and gets R2 to verify the correctness.");

		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (NoSuchPaddingException e) {
			System.err.println(e.getMessage());
		} catch (GeneralSecurityException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
