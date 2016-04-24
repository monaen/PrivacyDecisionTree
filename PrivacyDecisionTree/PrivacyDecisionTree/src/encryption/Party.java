package encryption;

import java.math.*;
public class Party {
   private BigInteger x;
   private BigInteger a;
   
   // Random numbers need to be generated
   private BigInteger z;
   private BigInteger s1;
   private BigInteger s2;
   
   public Paillier paillier = new Paillier();
   
   Party(BigInteger x,BigInteger a)
   {
	   this.x = x;
	   this.a = a;
	   init();
   }
   
   // initializing the random variables
   public void init()
   {	   
	   z = new BigInteger(String.valueOf((int)(1+Math.random()*100)));
	   s1 = new BigInteger(String.valueOf((int)(1+Math.random()*100)));
	   s2 = new BigInteger(String.valueOf((int)(1+Math.random()*100)));
   }
   
   public BigInteger getX()
   {
	   return x;
   }
   
   public BigInteger getA()
   {
	   return a;
   }
   
   public BigInteger getZ()
   {
	   return z;
   }
   
   
   public BigInteger getS1()
   {
	   return s1;
   }
   
   
   public BigInteger getS2()
   {
	   return s2;
   }
}
