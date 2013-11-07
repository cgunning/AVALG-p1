import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

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
		
		if(n.isProbablePrime(20)) {
			factors.add(number);
			return factors;
		}
		
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
		
		BigInteger f = KnownPrimes.factor(n);
		if(f != null)
			return f;
		
		BigInteger x = TWO;
		BigInteger y = TWO;
		BigInteger d = ONE;
		
		if(n.mod(TWO).compareTo(ZERO) == 0)
			return TWO;
		
		while(d.compareTo(ONE) == 0) {
			x = fun(x);
			y = fun(fun(y));
			d = GCD(x, y);
			long now = System.currentTimeMillis();
			if(now >= deadline)
 				throw new TimeLimitExceededException();
		}
		
		if(d.compareTo(n) == 0 && !d.isProbablePrime(20)) {
			return null;
		}
		return d;
	}
	
	public BigInteger fun(BigInteger x) {
		return x.pow(2).subtract(ONE).mod(n);
	}
	
	public BigInteger GCD(BigInteger x, BigInteger y) {
		return n.gcd(x.subtract(y).abs());
	}
}
