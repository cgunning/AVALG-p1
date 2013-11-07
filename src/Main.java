import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.math.BigInteger;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<BigInteger> number = new ArrayList<BigInteger>();
		ArrayList<LinkedList<String>> answers = new ArrayList<LinkedList<String>>();
		BigInteger TWO = new BigInteger("2");
		
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		
		String line;
		line = br.readLine();
		while(line.length() >= 1){//line != null ) {//    //I can't run it on my computer  with line != null but it is nessesary for KATTIS
			BigInteger tmp = new BigInteger(line);
			number.add(tmp);
			line = br.readLine();
		} 
		
		for(int i = 0; i < number.size(); i++) {
			answers.add(new LinkedList<String>());
			BigInteger newNumber = number.get(i);
			
			if(newNumber.isProbablePrime(2))
			{
				answers.get(i).add(newNumber.toString());
				continue;				
			}
			if(newNumber.bitLength() > 70) {
				answers.get(i).add("fail");
				continue;
			}
			
			if(newNumber.mod(TWO).compareTo(BigInteger.ZERO) == 0 || newNumber.bitLength() < 50)
			{
				BigInteger start = new BigInteger("2");			
				BigInteger factorNr = factor(newNumber, start);
				
				// Primtal
				if(factorNr == BigInteger.ZERO) {
					answers.get(i).add(newNumber.toString());
					continue;
				}
	
				while(factorNr != BigInteger.ZERO) {	
					start = factorNr;
					answers.get(i).add(factorNr.toString());
					newNumber = newNumber.divide(factorNr);
					
					factorNr = factor(newNumber, start);
				}			
								
				answers.get(i).add(newNumber.toString());				
			}
			
			else
			{
				BigInteger[] smallerN = fermat(newNumber);
				//System.out.println(smallerN[0] + " " + smallerN[1] + "|| ");
				for(int j = 0; j < 2; j++)
				{
					BigInteger start = new BigInteger("2");			
					BigInteger factorNr = factor(smallerN[j], start);
					
					// Primtal
					if(factorNr == BigInteger.ZERO) {
						answers.get(i).add(smallerN[j].toString());
						continue;
					}
		
					while(factorNr != BigInteger.ZERO) {	
						start = factorNr;
						answers.get(i).add(factorNr.toString());
						smallerN[j] = smallerN[j].divide(factorNr);
						
						factorNr = factor(smallerN[j], start);
						
						
					}
					
					answers.get(i).add(smallerN[j].toString());
				}
			}
		}

		
		print(answers);
	}
	
	private static BigInteger factor(BigInteger number, BigInteger start) {
		BigInteger bound = new BigInteger("" + (int)Math.sqrt(number.doubleValue()));
		BigInteger START = start;
		//BigInteger TWO = new BigInteger("2");
		//int test = TWO.compareTo(bound);
		for(BigInteger i = START; i.compareTo(bound) <= 0; i = i.add(BigInteger.ONE)) {
			if(number.mod(i).compareTo(BigInteger.ZERO) == 0) {
				return i;
			}
		}
		
		return BigInteger.ZERO;
	}
	
	private static void print(ArrayList<LinkedList<String>> answers)
	{
		for(int i = 0; i < answers.size(); i++) {
			LinkedList<String> primes = answers.get(i);
			for(String prime : primes) {
				System.out.println(prime);
			}
			if(i != (answers.size() - 1))
				System.out.println();
		}
		
	}
	
	private static BigInteger[] fermat(BigInteger n)
	{
		BigInteger[] pair = new BigInteger[2];
		
		BigInteger TWO = new BigInteger("2");
		BigInteger s =  new BigInteger("" + (int)Math.sqrt(n.doubleValue()));
		BigInteger u = (s.multiply(TWO)).add(BigInteger.ONE);
		BigInteger v = BigInteger.ONE;
		BigInteger r = (s.pow(2)).subtract(n);
		
		while(r != BigInteger.ZERO)
		{
			while(r.compareTo(BigInteger.ZERO) > 0)
			{
				r = r.subtract(v);//  r-v;
				v = v.add(TWO);//v+2;
			}
			
			while(r.compareTo(BigInteger.ZERO) < 0)
			{
				r = r.add(u);
				u = u.add(TWO);
			}
		}
		
		BigInteger a = (u.add(v).subtract(TWO)).divide(TWO);
		pair[0] = a;
		BigInteger b = (u.subtract(v)).divide(TWO);
		pair[1] = b;
		
		return pair;
		
		
	}
	
	
	
	
} 