import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.naming.TimeLimitExceededException;


public class PollardRho {

	static BigInteger ZERO = BigInteger.ZERO;
	static BigInteger ONE = BigInteger.ONE;
	static BigInteger TWO = new BigInteger("2");
	BigInteger n;
	
	long deadline;
	
	public PollardRho() {
	}
	
	public LinkedList<BigInteger> getFactors(BigInteger number, long deadline) throws TimeLimitExceededException {
		this.n = number;
		this.deadline = deadline;
		LinkedList<BigInteger> factors = new LinkedList<BigInteger>();
		
		if(n.isProbablePrime(2)) {
			factors.add(number);
			return factors;
		}
		
		KnownPrimes.setStartIndex(0);
		
		BigInteger factorNr = factor(n);
		
		// Misslyckades
		if(factorNr == null) {
			return null;
		}
		
		while(factorNr.compareTo(ONE) != 0 && factorNr.compareTo(n) != 0) {
			factors.add(factorNr);
			n = n.divide(factorNr);
			
			factorNr = factor(n);
			
			// misslyckades
			if(factorNr == null) {
				return null;
			}
		}	
		factors.add(n);
		
		return factors;
	}
	
	public BigInteger factor(BigInteger n) throws TimeLimitExceededException {
		if(n.isProbablePrime(10))
			return n;
		
		
		BigInteger f = KnownPrimes.factor(n, deadline);
		if(f != null)
			return f;
		
		Random rnd = new Random();
		
		
		BigInteger x = TWO;
		BigInteger y = TWO;
		BigInteger d = ONE;
		
		while(true) {
			BigInteger c = new BigInteger(n.bitLength()-1, rnd);
			
			while(d.compareTo(ONE) == 0) {
				x = fun(x, c);
				y = fun(fun(y, c), c);
				d = GCD(x, y);
				long now = System.currentTimeMillis();
				if(now >= deadline)
	 				throw new TimeLimitExceededException();
			}
			
			if(d.compareTo(n) != 0 && d.isProbablePrime(2)) {
				return d;
			}
		}
	}
	
	public BigInteger fun(BigInteger x, BigInteger c) {
		return x.pow(2).add(c).mod(n);
	}
	
	public BigInteger GCD(BigInteger x, BigInteger y) {
		return n.gcd(x.subtract(y).abs());
	}
}
