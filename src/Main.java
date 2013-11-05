import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.math.BigInteger;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<BigInteger> number = new ArrayList<BigInteger>();
		ArrayList<LinkedList<String>> answers = new ArrayList<LinkedList<String>>();
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		
		String line;
		line = br.readLine();
		while(line != null) {
			BigInteger tmp = new BigInteger(line);
			number.add(tmp);
			line = br.readLine();
		} 
		
		for(int i = 0; i < number.size(); i++) {
			answers.add(new LinkedList<String>());
			BigInteger newNumber = number.get(i);
			
			if(newNumber.bitLength() > 54) {
				answers.get(i).add("fail");
				continue;
			}
			
			BigInteger factorNr = factor(newNumber);
			
			// Primtal
			if(factorNr == BigInteger.ZERO) {
				answers.get(i).add(newNumber.toString());
				continue;
			}
			
			while(factorNr != BigInteger.ZERO) {				
				answers.get(i).add(factorNr.toString());
				newNumber = newNumber.divide(factorNr);
				
				factorNr = factor(newNumber);
			}	
			answers.get(i).add(newNumber.toString());
		}

		
		print(answers);
	}
	
	private static BigInteger factor(BigInteger number) {
		BigInteger bound = new BigInteger("" + (int)Math.sqrt(number.doubleValue()));
		BigInteger TWO = new BigInteger("2");
		int test = TWO.compareTo(bound);
		for(BigInteger i = TWO; i.compareTo(bound) <= 0; i = i.add(BigInteger.ONE)) {
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
} 