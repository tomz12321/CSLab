/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.random.CryptoRandom;
import org.apache.commons.crypto.random.CryptoRandomFactory;
import org.apache.commons.crypto.utils.Utils;
import org.apache.commons.crypto.cipher.CryptoCipher;

public class Lab9 {

    public static void main(String args[]) throws IOException, GeneralSecurityException {
        final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("s3613654s3613654s3613654s3613654"), "AES");
        final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("s3613654s3613654"));
        final String transform = "AES/CBC/PKCS5Padding";
        System.out.println(key);
        byte[] r1 = getRandom();
        System.out.println("R1 is " + Arrays.toString(r1));
        byte[] r2 = getRandom();
        System.out.println("R2 is " + Arrays.toString(r2));
        Properties properties = new Properties();

        CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
        System.out.println("Cipher:  " + encipher.getClass().getCanonicalName());
        System.out.println("Input Message to Encrypt");
        Scanner sc = new Scanner(System.in);
        String sampleInput = sc.next();
        System.out.println("input:  " + sampleInput);

        byte[] input = getUTF8Bytes(sampleInput);
        byte[] output = new byte[32];//32 bytes= 256 bits

        encipher.init(Cipher.ENCRYPT_MODE, key, iv);
        // Continues a multiple-part encryption/decryption operation for byte
        // array.
        int updateBytes = encipher.update(input, 0, input.length, output, 0);
        System.out.println(updateBytes);
        // We must call doFinal at the end of encryption/decryption.
        int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
        System.out.println(finalBytes);
        // Closes the cipher.
        encipher.close();

        System.out.println(Arrays.toString(Arrays.copyOf(output, updateBytes + finalBytes)));

        // Now reverse the process using a different implementation with the
        // same settings
        //teproperties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.JCE.getClassName());
        CryptoCipher decipher = Utils.getCipherInstance(transform, properties);
        System.out.println("Cipher:  " + encipher.getClass().getCanonicalName());

        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decoded = new byte[32];
        decipher.doFinal(output, 0, updateBytes + finalBytes, decoded, 0);

        System.out.println("output: " + new String(decoded, StandardCharsets.UTF_8));
    }

    private static byte[] getRandom() throws IOException, GeneralSecurityException {
        byte[] r = new byte[32];

        Properties properties = new Properties();
        // properties.put(CryptoRandomFactory.CLASSES_KEY,CryptoRandomFactory.RandomProvider.OPENSSL.getClassName());
        try (CryptoRandom random = CryptoRandomFactory.getCryptoRandom(properties)) {

            System.out.println(random.getClass().getCanonicalName());

            random.nextBytes(r);

        }

        System.out.println(Arrays.toString(r));
        return r;
    }

    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}


