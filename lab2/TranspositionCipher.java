import java.util.*;
import java.io.*;
import java.lang.*;

public class TranspositionCipher {
    public static void main(String[] args) {
    		Scanner scan = new Scanner(System.in);
        String line = System.getProperty("line.separator");
        scan.useDelimiter(line);

        System.out.print("Press 1 to \nEncryt 2 to Decrypt : ");
        int option = scan.nextInt();
        switch (option) {
            case 1:
            		//THEME,ETING,hasbe,enpos,tpone,dunti,lnext,month (length:40)
                System.out.print("Enter String:");
                String text = scan.next().trim();
                
                int sectionCount = 0;
                String newSentence = "";
                //for (0->text.length-1) for (0->5)
                for (int i = 0; i < text.length(); i= i + 5)
                {
	                for (int j = 0; j < 5 ; j++)
	                {
	                		//text.charAt(i+j);
	                		newSentence = newSentence + text.charAt(i+j);
	                }
	                sectionCount++ ;
	                //System.out.println("sectionCount:"+sectionCount);
	                if (i + 5 != text.length())
	                		newSentence = newSentence + (",");
	                //System.out.println("newSentence:"+newSentence);
	                
                }
                //String[] strArray = new String[3];
                String[] fullSentence = new String[sectionCount];
                
                //hard code first
                //String inputSentence = "THEMEETINGhasbeenpostponeduntilnextmonth"
                //String secSentence = "THEME,ETING,hasbe,enpos,tpone,dunti,lnext,month";
                //sectionCount = 8;
                //String[] attribute = secSentence.split(",");
                String[] attribute = newSentence.split(",");
                
                System.out.print("Enter Key:");
                String key = scan.next();
                
                //(for (0->n))
                for (int k = 0; k < sectionCount; k++ )
                {
                		//System.out.println(encryptCT(key, text).toUpperCase());
                		System.out.println(encryptCT(key, attribute[k]).toLowerCase());
                }
                break;
            case 2:
                System.out.print("Enter Encrypted String:");
                text = scan.next();

                System.out.print("Enter Key:");
                key = scan.next();

                System.out.println(decryptCT(key, text));
                break;
            default:
                break;
        }
    }

    public static String encryptCT(String key, String text) {
        int[] arrange = arrangeKey(key);

        int lenkey = arrange.length;
        int lentext = text.length();

        int row = (int) Math.ceil((double) lentext / lenkey);

        char[][] grid = new char[row][lenkey];
        int z = 0;
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < lenkey; y++) {
                if (lentext == z) {
                  
                    grid[x][y] = RandomAlpha();
                    z--;
                } else {
                    grid[x][y] = text.charAt(z);
                }

                z++;
            }
        }
        String enc = "";
        for (int x = 0; x < lenkey; x++) {
            for (int y = 0; y < lenkey; y++) {
                if (x == arrange[y]) {
                    for (int a = 0; a < row; a++) {
                        enc = enc + grid[a][y];
                    }
                }
            }
        }
        return enc;
    }

    public static String decryptCT(String key, String text) {
        int[] arrange = arrangeKey(key);
        int lenkey = arrange.length;
        int lentext = text.length();

        int row = (int) Math.ceil((double) lentext / lenkey);

        String regex = "(?<=\\G.{" + row + "})";
        String[] get = text.split(regex);

        char[][] grid = new char[row][lenkey];

        for (int x = 0; x < lenkey; x++) {
            for (int y = 0; y < lenkey; y++) {
                if (arrange[x] == y) {
                    for (int z = 0; z < row; z++) {
                        grid[z][y] = get[arrange[y]].charAt(z);
                    }
                }
            }
        }

        String dec = "";
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < lenkey; y++) {
                dec = dec + grid[x][y];
            }
        }

        return dec;
    }

    public static char RandomAlpha() {
        //generate random alpha for null space
        Random r = new Random();
        return (char)(r.nextInt(26) + 'a');
    }

    public static int[] arrangeKey(String key) {
        //arrange position of grid
        String[] keys = key.split("");
        Arrays.sort(keys);
        int[] num = new int[key.length()];
        for (int x = 0; x < keys.length; x++) {
            for (int y = 0; y < key.length(); y++) {
                if (keys[x].equals(key.charAt(y) + "")) {
                    num[y] = x;
                    break;
                }
            }
        }

        return num;
    }

}