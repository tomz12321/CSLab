package test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import common.CryptoHash;
import common.CryptoRandomGenerator;
import common.Util;

public class ShamirTest {

	public static void main(String args[]) throws GeneralSecurityException, IOException {
		// Alice's ID
		String id = "s3703131";
		byte[] idByte = Util.getUTF8Bytes(id);
		BigInteger idInt = BigInteger.valueOf(Math.abs(id.hashCode()));
		System.out.println("Alice's id:" + id);
		System.out.println("Alice's id int:" + idInt);

		String msg = "Cloud security";
		byte[] msgByte = Util.getUTF8Bytes(msg);
		System.out.println("Alice's message:" + msg);
		System.out.println("Alice's message byte:" + Arrays.toString(msgByte));

		Random ran = new Random();
		BigInteger p = BigInteger.probablePrime(512, ran);
		BigInteger q = BigInteger.probablePrime(512, ran);
		System.out.println("prime p :" + p.toString());
		System.out.println("prime q :" + q.toString());

		BigInteger n = p.multiply(q);
		//byte[] nByte = n.toByteArray();
		//String nBase64 = Base64.encodeBase64String(nByte);
		System.out.println("modulus n :" + n.toString());
		System.out.println("bitLength:" + n.bitLength());
		
		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		BigInteger e = BigInteger.valueOf(65537);
		BigInteger d = e.modInverse(phi);
		BigInteger test = (e.multiply(d)).mod(phi);
		System.out.println("e:" + e.toString());
		System.out.println("d:" + d.toString());
		//System.out.println("test:"+test.toString());

		BigInteger sk = idInt.modPow(d, n);
		String skAliceBase64 = Base64.encodeBase64String(sk.toByteArray());
		System.out.println("[PKG side:] Sign Alice's secret key (Base64 encoded):" + skAliceBase64);
		
		BigInteger sktest = sk.modPow(e, n);
		//System.out.println("sktest:" + sktest.toString());

		// choose random r
		CryptoRandomGenerator ranGen = new CryptoRandomGenerator();
		int length = 32; // 32 bytes = 256 bits
		byte[] r = ranGen.genCryptoRandom(length);
		BigInteger rInt = new BigInteger(r);
		String rBase64 = Base64.encodeBase64String(r);
		System.out.println("[Alice side:] Alice generates random R" );

		// t = r^e(mod n)
		BigInteger t = rInt.modPow(e, n);
		System.out.println("[Alice side:] t:" + t.toString());
		byte[] tByte = t.toByteArray();

		// alice generate H(t, m)
		byte[] tm = Util.concatenateByteArray(tByte, msgByte);
		byte[] tmHash = CryptoHash.genSha256DigestMulti(tm, 1); // 256 bits = 32 bytes
		BigInteger hash = new BigInteger(tmHash);
		String tmHashBase64 = Base64.encodeBase64String(tmHash);
		System.out.println("[Alice side:] Generate hash of t||m. (SHA-256, 32 Bytes)");
		System.out.println("Digest (Base64):" + tmHashBase64);

		// r^hash(mod n)
		BigInteger rr = rInt.modPow(hash, n);
		BigInteger s = (sk.multiply(rr)).mod(n);
		System.out.println("[Alice side:] Generate s:" + s.toString());
		System.out.println("[Alice side:] Signature (s,t) (base64):" + Base64.encodeBase64String(s.toByteArray())
				+ Base64.encodeBase64String(tByte));
		
		// s^e mod n
		BigInteger v1 = s.modPow(e, n);
		BigInteger v2 = (idInt.multiply(t.modPow(hash, n))).mod(n);
		System.out.println("[Bob side:] Generate s^e (mod n):" + v1.toString());
		System.out.println("[Bob side:] Generate ID*t^hash (mod n):" + v2.toString());
		if(v1.equals(v2)) {
			System.out.println("Valid message.");
		}else {
			System.out.println("Invalid message!!");
		}

	}
	

}
