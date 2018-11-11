package test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import common.AesEncryption;
import common.CryptoHash;
import common.CryptoRandomGenerator;
import common.KeyGeneratorDH;
import common.KeyGeneratorRsa;
import common.Rsa;

public class MutualAuthSK {
	
	public static void main(String[] args) {
		// use your password as the AES key trigger - 256 bits
		String secretTrigger = "s3703131s3703131s3703131s3703131";
		String ivTrigger = "s3703131s3703131";
		System.out.println("[Shared password]:"+secretTrigger);
			

		
		try {
			// Alice sends id
			System.out.println("[Alice side]: Alice sends id to Bob.");
			
			// Bob generates challenge R1 and sends to Alice
			CryptoRandomGenerator ranGen = new CryptoRandomGenerator();
			int length = 32; //32 bytes = 256 bits
			byte[] r1 = ranGen.genCryptoRandom(length);			
			String r1Base64 = Base64.encodeBase64String(r1);		
			System.out.println("[Bob side]: Bob generates challenge R1 randomly. R1:"+ r1Base64);
			
			// Alice generates R2
			byte[] r2 = ranGen.genCryptoRandom(length);		
			String r2Base64 = Base64.encodeBase64String(r2);
			System.out.println("[Alice side]: Alice generates challenge R2 randomly. R2:"+ r2Base64);
			
			// Alice side AES_pw(public key)
			AesEncryption aesEnc = new AesEncryption(secretTrigger, ivTrigger);
			byte[] r1Cipher = aesEnc.encAes256CbcByte(r1);
			String r1CipherBase64 = Base64.encodeBase64String(r1Cipher);
			System.out.println("[Alice side]: Alice sends encrypted Enc(R1) and R2 to Bob: "+r1CipherBase64);
			
			// Bob decrypts Enc(R1) to verify
			System.out.println("[Bob side]: Bob decrypts and gets R1 to verify the correctness.");
			
			// Bob ebcrypts R2
			byte[] r2Cipher = aesEnc.encAes256CbcByte(r2);
			String r2CipherBase64 = Base64.encodeBase64String(r2Cipher);
			System.out.println("[Bob side]: Bob sends encrypted Enc(R2) and R1 to Alice: "+r2CipherBase64);
			
			System.out.println("[Alice side]: Alice decrypts and gets R2 to verify the correctness.");
			
				
			
			

		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (NoSuchPaddingException e) {
			System.err.println(e.getMessage());
		} catch (GeneralSecurityException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
