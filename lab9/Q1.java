/**
 * Lab 9 : Java work 
 * This week’s lab concentrates on password-based authentication between two parties. In
 * the first task, we implement a Java program to perform the mutual authentication with
 * secret key. Although the mutual authentication protocol allows two parties
 * authenticating each other, it is not robust enough to withstand the reflection attack.
 * Thus, in the second task, we implement a Java program of mutual authentication
 * protocol with improved security strength.
 */

//SHA3 Java 9 &amp; 10 include the SHA3 hash function family. Specification can be found at
//(http://javadoc.iaik.tugraz.at/iaik_jce/current/iaik/security/md/SHA3_256.html).

//AES Apache commons crypto lib

// crypto random generator.
// Download at (https://commons.apache.org/proper/commons-crypto/download_crypto.cgi). 

//User guide and examples can be found at 
//(https://commons.apache.org/proper/commons-crypto/userguide.html). 
//The usage of ByteBuffer-based algorithm is superior in handling long plaintext.

//Apache commons codec lib provides binary encoding methods, e.g. Base64.
//Download at (https://commons.apache.org/proper/commons-codec/download_codec.cgi ).

//Input Student id

//Task 1 Implementation of Mutual authentication with secret key

//
// Figure 1 illustrates the protocol of mutual authentication with secret key.
// - In this task, we use our student no. as shared password. We derive a 256-bit secret
// key from repeating student no 4 times. For instance, the student no. is “s3701234”,
// then the secret key is “s3613252s3613252s3613252s3613252”.
// - R1, R2 are challenges generated by Alice and Bob respectively. We use two 256-bit
// crypto random numbers as them. The following code may help you.

//Implement a Java program of mutual authentication protocol with shared
//password. Output the intermediate results and shared password between Alice
//and Bob.

byte[] r = new byte[length];
Properties properties = new Properties();
// comment out the following line if error occurs
properties.put(CryptoRandomFactory.CLASSES_KEY, CryptoRandomFactory.RandomProvider.OPENSSL.getClassName());

try (CryptoRandom random = CryptoRandomFactory.getCryptoRandom(properties)){

	// Show the actual class (may be differenet from the one requested)
	System.out.println(random.getClass().getCanonicalName());

	random.nextBytes(r);
	return r;
}

// E(R1, K) denotes the AES encryption of R1 under the shared secret key K. In our
// algorithm, we use AES-256-CBC mode. In this AES mode, apart from the 256-bit
// secret key, we also need a 128-bit initial vector (iv).

//Task2 Implementation of mutual authentication with improved security

// The mutual authentication protocol introduced above allows both parties in a
// communication link authenticate each other. As a result, the server will grant access to

// legitimate users. However, it is not robust to withstand the reflection attack. Specifically,
// as Figure 2 illustrates, the attacker Trudy initiates a connection to Bob, and Bob
// attempts to authenticate Trudy by sending him a challenge R1. After receiving R1, Trudy
// opens another connection to Bob, and sends R1 to Bob as its own. Bob responds to the
// challenge R1 with E(R1, K). Then Trudy sends that response back to Bob on the original
// connection. As a result, Trudy can impersonate a legitimate client and communicate
// with Bob.
// There are two mitigation strategies as using separate keys K, K 2 derived from the shared
// password for each direction. The other one is restricting the challenges R1, R2.

// In this task, we develop a Java program of mutual authentication protocol with
// improved security. As Figure 3 shows,
// - We use our student no. as the shared password K.
// - K1 is the message digest H(K) of password K generated from standardized crypto
// hash function. K2 is the message digest H(H(K)) hashed twice. In our algorithm, we
// use SHA3-256. JDK 9 or JDK 10 is required. Following code may help you.

final static String HASH_SHA3_256 = "SHA3-256";

public static byte[] genSha3Digest256Multi(String msg, int round) {
	byte[] input = AesEncryption.getUTF8Bytes(msg);
	MessageDigest sha3;
	byte[] hash256 = new byte[32];
	try {
		sha3 = MessageDigest.getInstance(HASH_SHA3_256);
		for (int i = 0; i < round-1; i++) {
			sha3.update(input);
		}

		hash256 = sha3.digest();
	} catch (NoSuchAlogrithmException e) {
		System.err.println("ERROR || SHA3-256 NoSuchAlgorithmException.");
		e.printStackTrace();
	}

	return hash256;
}

//Challenge R1, R2 are two crypto random numbers, where R1 is an odd number yet number.