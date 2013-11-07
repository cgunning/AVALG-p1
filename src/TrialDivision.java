import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;


public class TrialDivision {

	static BigInteger ZERO = BigInteger.ZERO;
	static BigInteger ONE = BigInteger.ONE;
	static BigInteger TWO = new BigInteger("2");
	
	TrialDivision() {
		
	}
	
	public LinkedList<BigInteger> getFactors(BigInteger n) {
		BigInteger newNumber = n;
		LinkedList<BigInteger> factors = new LinkedList<BigInteger>();
		
		BigInteger start = TWO;
		BigInteger factorNr = factor(newNumber, start);
		
		// Primtal
		if(factorNr == null) {
			factors.add(newNumber);
			return factors;
		}
		
		while(factorNr != null) {	
			start = factorNr;
			factors.add(factorNr);
			newNumber = newNumber.divide(factorNr);
			
			factorNr = factor(newNumber, start);
		}	
		factors.add(newNumber);
		
		return factors;
	}
	
	public BigInteger factor(BigInteger n, BigInteger start) {
		BigInteger bound = new BigInteger("" + (int)Math.sqrt(n.doubleValue()));
		BigInteger TWO = new BigInteger("2");
		for(BigInteger i = start; i.compareTo(bound) <= 0; i = i.add(BigInteger.ONE)) {
			if(n.mod(i).compareTo(BigInteger.ZERO) == 0) {
				return i;
			}
		}
		
		return null;
	}
}
