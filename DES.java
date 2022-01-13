package symmetric;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class DES {
	public static void main(String[] args) throws Exception{
		byte[] fullct = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");//set up values
		byte[] ky = CryptoTools.hexToBytes("4F75725269676874");
		byte[] iv = CryptoTools.hexToBytes("496E566563746F72");
		for (int i = 8; i < fullct.length + 8; i = i + 8) {//divide ct into blocks the size of the key
			byte[] ct = new byte[8];
			int y = 0;
			for(int x = i - 8; x < i; x++) {
				if(fullct.length > x) {//make sure we only take the ct values
					ct[y] = fullct[x];
				}
				y++;
			}
			byte[] xorCt = new byte[8];
			for (int z = 0; z < ct.length; z++) {//XORthe ciphertext with the feedback
				xorCt[z] = (byte) (ct[z] ^ iv[z]);
			}
			iv = ct;
			SecretKeySpec secret = new SecretKeySpec(ky, "DES");
			
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			
			cipher.init(Cipher.DECRYPT_MODE, secret);
			
			byte[] pt = cipher.doFinal(xorCt);
			for (int x = 0; x < pt.length; x++) {
				System.out.print(Character.toString((char)pt[x]));
			}
		}
		
	}

}
