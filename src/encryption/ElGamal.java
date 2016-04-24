package encryption;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ElGamal {
	BigInteger p, g; // �������ͱ�ԭԪ
	int alpha = 64;//��Կ��λ��
	BigInteger y;//��Կ  ��Կ�� p,g,y
	BigInteger a;
	ElGamal()
	{
		getRandomP();
		getRandoma();
		calculatey();
	}
	/** ȡһ������������P,��P������Ԫa */
	public void getRandomP() {
		Random r = new Random();
		BigInteger q = null;
		while (true) {
			q = BigInteger.probablePrime(alpha, r);
			if (q.bitLength() != alpha)
				continue;
			if (q.isProbablePrime(10)) // ���qΪ�������ٽ�һ����������Ԫ
			{
				p = q.multiply(new BigInteger("2")).add(BigInteger.ONE);
				if (p.isProbablePrime(10)) // ���PΪ������OK~���������
					break;
			}
		}
		while (true) {
			g = BigInteger.probablePrime(p.bitLength() - 1, r);
			if (!g.modPow(BigInteger.ONE, p).equals(BigInteger.ONE)
					&& !g.modPow(q, p).equals(BigInteger.ONE)) {
				break;
			}
		}
	}

	/** ȡ�����a */
	public void getRandoma() {		
		Random r = new Random();
		a = new BigInteger(p.bitLength() - 1, r);
	}

	/** ������Կ��ֵ */
	public void calculatey() {
		y = g.modPow(a, p);
	}

	/** �Լ����� */
	public BigInteger[] Encryption(BigInteger message) {
		BigInteger[] C = new BigInteger[2];
		Random r = new Random();
		BigInteger k;
		while (true) {
			k = new BigInteger(p.bitLength() - 1, r);// ����һ0<=k<p-1�������
			if (k.gcd(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) {// ����������p-1����
																			// ��ѡȡ�ɹ�,���������k
				break;
			}
		}
		// ��������
		C[0] = g.modPow(k, p);
		C[1] = message.multiply(y.modPow(k, p)).mod(p);
		// �������ĵ�������        
		return C;
	}
	
	/**�ù�Կ����*/
	public BigInteger[] EncryptionByPK(BigInteger message,BigInteger[] key)
	{
		BigInteger[] C = new BigInteger[2];
		Random r = new Random();
		BigInteger k;
		while (true) {
			k = new BigInteger(key[0].bitLength() - 1, r);// ����һ0<=k<p-1�������
			if (k.gcd(key[0].subtract(BigInteger.ONE)).equals(BigInteger.ONE)) {// ����������p-1����
																			// ��ѡȡ�ɹ�,���������k
				break;
			}
		}
		// ��������
		C[0] = key[1].modPow(k, key[0]);
		C[1] = message.multiply(key[2].modPow(k, key[0])).mod(key[0]);
		// �������ĵ�������        
		return C;
	}

	/** ���� */
	public  BigInteger Decryption(BigInteger[] cipher) {
		BigInteger message = cipher[1].multiply(cipher[0].modPow(a.negate(), p)).mod(p);
		//String str = new String(scy.toByteArray());// �ѷ��صĽ����ԭ��һ���ִ�
		return message;
	}
	
	//�õ���Կ
	public BigInteger[] getKey()
	{
		BigInteger[] key= new BigInteger[3];
		key[0] = p;
		key[1] = g;
		key[2] = y;
		return key;
	}
}