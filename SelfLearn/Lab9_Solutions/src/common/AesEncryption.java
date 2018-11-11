package common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

public class AesEncryption {

	final static String AES = "AES";
	final static String MODE_CBC = "CBC";
	final static String PADDING_PKCS5 = "PKCS5Padding";

	static SecretKeySpec secretKey;
	static IvParameterSpec iv;

	public AesEncryption(String secretKeyTrigger, String ivTrigger) {
		secretKey = new SecretKeySpec(getUTF8Bytes(secretKeyTrigger), AES);
		iv = new IvParameterSpec(getUTF8Bytes(ivTrigger));

	}
	
	public AesEncryption(byte[] secretKeyTrigger, String ivTrigger) {
		secretKey = new SecretKeySpec((secretKeyTrigger), AES);
		iv = new IvParameterSpec(getUTF8Bytes(ivTrigger));

	}
	
	public AesEncryption(String secretKeyTrigger, byte[] ivTrigger) {
		secretKey = new SecretKeySpec(getUTF8Bytes(secretKeyTrigger), AES);
		iv = new IvParameterSpec((ivTrigger));

	}

	public AesEncryption(byte[] secretKeyTrigger, byte[] ivTrigger) {
		secretKey = new SecretKeySpec(secretKeyTrigger, AES);
		iv = new IvParameterSpec(ivTrigger);
	}

	public String encAes128CbcStr(String plaintext) {
		// convert pliantext passwd to UTF_8
		byte[] plaintextByte = getUTF8Bytes(plaintext);

		// create a cryptoCipher instance
		final String transform = AES + "/" + MODE_CBC + "/" + PADDING_PKCS5;
		System.out.println("transform|" + transform + "|");

		return Arrays.toString(encAesBuffer(transform, plaintextByte, plaintextByte.length));
	}

	public byte[] encAes128CbcByte(byte[] plaintextByte) {

		// create a cryptoCipher instance
		final String transform = AES + "/" + MODE_CBC + "/" + PADDING_PKCS5;
		System.out.println("transform|" + transform + "|");

		return encAesBuffer(transform, plaintextByte, plaintextByte.length);
	}

	public String encAes256CbcStr(String plaintext) {
		// convert pliantext passwd to UTF_8
		byte[] plaintextByte = getUTF8Bytes(plaintext);

		// create a cryptoCipher instance
		final String transform = AES + "/" + MODE_CBC + "/" + PADDING_PKCS5;
		System.out.println("transform|" + transform + "|");

		return Arrays.toString(encAesBuffer(transform, plaintextByte, plaintextByte.length));
	}

	public byte[] encAes256CbcByte(byte[] plaintextByte) {

		// create a cryptoCipher instance
		final String transform = AES + "/" + MODE_CBC + "/" + PADDING_PKCS5;
		System.out.println("transform|" + transform + "|");

		return encAesBuffer(transform, plaintextByte, plaintextByte.length);
	}

	public byte[] encAesBuffer(String transform, byte[] plaintextByte, int outputLength) {
		Properties properties = new Properties();
		final ByteBuffer outBuffer;
		final int bufferSize = 1024;
		final int updateBytes;
		final int finalBytes;
		// Creates a CryptoCipher instance with the transformation and properties.
		try (CryptoCipher encipher = Utils.getCipherInstance(transform, properties)) {

			ByteBuffer inBuffer = ByteBuffer.allocateDirect(bufferSize);
			outBuffer = ByteBuffer.allocateDirect(bufferSize);
			inBuffer.put(plaintextByte);

			inBuffer.flip(); // ready for the cipher to read it
			// Show the data is there
			//System.out.println("inBuffer=" + asString(inBuffer));

			// Initializes the cipher with ENCRYPT_MODE,key and iv.
			encipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			// Continues a multiple-part encryption/decryption operation for byte buffer.
			updateBytes = encipher.update(inBuffer, outBuffer);
			//System.out.println("updateBytes=" + updateBytes);

			// We should call do final at the end of encryption/decryption.
			finalBytes = encipher.doFinal(inBuffer, outBuffer);
			//System.out.println("finalBytes=" + finalBytes);
			outBuffer.flip(); // ready for use as decrypt
			byte[] encoded = new byte[updateBytes + finalBytes];
			outBuffer.duplicate().get(encoded);
			String encodedString = Base64.encodeBase64String(encoded);
			//System.out.println("encodedString=" + encodedString);
			return encoded;
			
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | ShortBufferException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		return null;

	}

	public byte[] encAesSmallMsg(String transform, byte[] plaintextByte, int outputLength) {
		byte[] passwdCipherByte = new byte[outputLength];

		Properties properties = new Properties();
		// properties.setProperty(CryptoCipherFactory.CLASSES_KEY,
		// CipherProvider.OPENSSL.getClassName());

		try {
			CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
			System.out.println("Cipher: " + encipher.getClass().getCanonicalName());

			// Initializes the cipher with ENCRYPT_MODE, key and iv.
			encipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			// Continues a multiple-part encryption/decryption operation for byte array.
			int updateBytes = encipher.update(plaintextByte, 0, plaintextByte.length, passwdCipherByte, 0);
			System.out.println(updateBytes);
			// We must call doFinal at the end of encryption/decryption.
			int finalBytes = encipher.doFinal(plaintextByte, 0, 0, passwdCipherByte, updateBytes);
			System.out.println(finalBytes);
			// Closes the cipher.
			encipher.close();

			System.out.println("cipher:" + passwdCipherByte);

			// Arrays.copyOf(passwdCipherByte, updateBytes + finalBytes);
			// System.out.println("cipher2:"+passwdCipherByte);

			System.out.println(Arrays.toString(Arrays.copyOf(passwdCipherByte, updateBytes + finalBytes)));

		} catch (IOException e) {
			System.out.println("ERROR || IOException.");
			e.printStackTrace();
		} catch (ShortBufferException e) {
			System.out.println("ERROR || ShortBufferException.");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("ERROR || IllegalBlockSizeException.");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("ERROR || BadPaddingException.");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("ERROR || InvalidKeyException.");
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("ERROR || InvalidAlgorithmParameterException.");
			e.printStackTrace();
		}

		return passwdCipherByte;
	}

	public static byte[] getUTF8Bytes(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}

	public static String asString(ByteBuffer buffer) {
		final ByteBuffer copy = buffer.duplicate();
		final byte[] bytes = new byte[copy.remaining()];
		copy.get(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

}
