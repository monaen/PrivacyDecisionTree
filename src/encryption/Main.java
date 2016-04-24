package encryption;

import java.math.*;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("******************elGamal Encryption****************** ");
		
		//P1 
		ElGamal elGamalOfP1 = new ElGamal();
		
		BigInteger m = new BigInteger("1234");
		System.out.println("P1 Plaintext -- m:" + m);
		
		BigInteger[] c = null;
		c = elGamalOfP1.Encryption(m);//P1 Encryption
		System.out.println("P1 elGamal Encryption -- C1:" + c[0]+" , C2:" + c[1]);
		
		BigInteger decOfm = elGamalOfP1.Decryption(c);
		System.out.println("P1 Decryption -- m:" + decOfm);
       
		ElGamal elGamalOfP2 = new ElGamal();
		BigInteger[] keyOfP2 = elGamalOfP2.getKey();// Get Public Key of P2
		
		c = elGamalOfP1.EncryptionByPK(m, keyOfP2);// Encrypt P1 using Public Key of P2
		System.out.println("Encrypt P1 using Public Key of P2 m -- C1:" + c[0]+" , C2:" + c[1]);
		
		decOfm = elGamalOfP2.Decryption(c);//P2 Decryption
		System.out.println("P2 Decryption -- m:" + decOfm);
		
		
		System.out.println("******************Paillier Encryption****************** ");
		Paillier paillierOfP1 = new Paillier();		
		m = new BigInteger("5678");
		System.out.println("P1 Plaintext -- m:" + m);
		
		BigInteger Cp = null;
		Cp = paillierOfP1.Encryption(m);
		System.out.println("P1elGamal Encryption -- Cp:" + Cp);
		
		decOfm = paillierOfP1.Decryption(Cp);
		System.out.println("P1paillier Decryption -- m:" + decOfm);
		
		Paillier paillierOfP2 = new Paillier();
		BigInteger[] P_keyOfP2 = paillierOfP2.getKey();
		Cp = paillierOfP1.EncryptionByPK(m, P_keyOfP2);
		System.out.println("Encrypt P1 using Public Key of P2 -- Cp:" + Cp);
		decOfm = paillierOfP2.Decryption(Cp);//P2 Decryption
		System.out.println("P2 Decryption -- m:" + decOfm);
		
		
		
		
		
	}

}
