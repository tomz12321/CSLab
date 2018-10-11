/**
 * Write a description of class RSA here.
 * 
 * @author Jyh-woei Yang 
 * @version 09/10/2018
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	
	static final int P = 271; //211
	static final int Q = 353; //223
	static int phiN;
	static int e;
	static int d;
	static int n;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		boolean quit = false;
		long pTxt;
		long cTxt;
		
		// key generation
		genKey();
		
		do {
	        System.out.println("Please choose: \n1. Signing 2. Verifying 3. Exit");
	        int option = sc.nextInt();
	        switch (option) {
	            case 1:
	                System.out.println("Enter a number:");
	                pTxt = sc.nextInt();
	                cTxt = (int) rsa(pTxt, d);
	                System.out.println("After RSA signing: Here is the signed text\n" + cTxt + "\n");
	                break;
	            case 2:
	                System.out.println("Enter the encrypted number:");
	                cTxt = sc.nextInt();
	                pTxt = (int) rsa(cTxt, e);
	                System.out.println("After RSA verifying: if same, this is exactly the same signed text.\n" + pTxt + "\n");
	                break;
	            case 3:
	            	System.out.println("Good Bye!");
	        		System.exit(0);
	            default:
	                break;
	        }
		} while( !quit );
		
		sc.close();
		
	}
	
	public static void genKey() {
		
		System.out.println("P is: " + P);
		System.out.println("Q is: " + Q);
		
		n = P * Q;
		System.out.println("N is: " + n);
		phiN = (P-1) * (Q-1);
		System.out.println("phiN is: " + phiN);
		
		Random rand = new Random();
		do {
			e = rand.nextInt( phiN - 1 ) + 1;
//			System.out.println("GCD is " + GCD(phiN, e));
		} while( GCD(phiN, e) != 1 );
		System.out.println("e is: " + e);
		
		d = extEuclid(phiN, e);
		System.out.println("d is: " + d);
		
	}
	
	public static long rsa(long txt, int key) {
		
		String keyBi = Integer.toBinaryString(key);
//		System.out.println("keyBi is " + keyBi);
		String[] keyArr = keyBi.split("(?!^)");
		
		ArrayList<Long> txtList = new ArrayList<>();
		if( !keyArr[keyArr.length-1].equals("0") ){
			txtList.add(txt);
		}
		
		int count = 1;
		for( int i=keyArr.length-2 ; i>=0 ; i-- ){
			if( !keyArr[i].equals("0") ){
				long calc = txt;
				for(int j=1;j<=count;j++){
					calc = (calc * calc) % n;
				}
				txtList.add(calc);
			}
			count++;
		}
		
		/*Iterator<Long> iter2 = txtList.iterator();
		while(iter2.hasNext()){
			System.out.println("iter is " + iter2.next());
		}*/
		Iterator<Long> iter = txtList.iterator();
		iter = txtList.iterator();
		long res = iter.next();
//		System.out.println("res is " + res);
		while(iter.hasNext()){
			res = ( res * iter.next() % n );
//			System.out.println("res is " + res);
		}
//		System.out.println("res % n is " + res);
		return res;
	}
	
	//method GCD to generate public key (e)
	public static int GCD(int a, int b) {
		if (b==0) return a;
			return GCD(b,a%b);
	}
	
	//method extEuclid to generate public key (d)
    public static int extEuclid(int a, int b) {
		
    	int q, r, s0 = 1, t0 = 0, s1 = 0, t1 = 1, temp;
    	
    	while( b != 0 ){
    		
			q = a / b;
			r = a % b;
			
			a = b;
			b = r;
			
			temp = s1;
			s1 = s0 - q * s1;
			s0 = temp;
			
			temp = t1;
			t1 = t0 - q * t1;
			t0 = temp;
			
			/*System.out.println("---------------------------------");
			System.out.println("q is " + q + " r is " + r + " a is " + a + " b is " + b);
			System.out.println("s0 is " + s0 + " s1 is " + s1 + " t0 is " + t0 + " t1 is " + t1);*/
		}
    	/*System.out.println("---------------------------------");
    	System.out.println("s1 is " + s1);
    	System.out.println("t1 is " + t1);*/
    	
    	if( t0<0 ) {
    		t0 = t0 + phiN;
    	}
    	
    	return t0;
	}

}
