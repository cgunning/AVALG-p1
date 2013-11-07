import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.math.BigInteger;

import javax.naming.TimeLimitExceededException;

public class Main {

	static BigInteger ZERO = BigInteger.ZERO;
	static BigInteger ONE = BigInteger.ONE;
	static BigInteger TWO = new BigInteger("2");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//main2(null);
		ArrayList<LinkedList<BigInteger>> answers = new ArrayList<LinkedList<BigInteger>>();
		ArrayList<BigInteger> numbers = read();
		
		LinkedList<BigInteger> fail = new LinkedList<BigInteger>();
		fail.add(BigInteger.ZERO);
		
		PollardRho pollardRho = new PollardRho();
		TrialDivision trialDivision = new TrialDivision();
		
		long timeSlice = 50;
		
		for(int i = 0; i < numbers.size(); i++) {
			BigInteger currentNumber = numbers.get(i);
			try {
				if(currentNumber.mod(TWO).compareTo(BigInteger.ZERO) == 0 || currentNumber.bitLength() < 50) {
					answers.add(pollardRho.getFactors(currentNumber, (System.currentTimeMillis() + timeSlice)));	
				} else {
					BigInteger[] smallerN = fermat(currentNumber);
	
					answers.add(pollardRho.getFactors(smallerN[0], (System.currentTimeMillis() + timeSlice)));	
					if(answers.get(i) != null)
						answers.get(i).addAll(pollardRho.getFactors(smallerN[1], (System.currentTimeMillis() + timeSlice)));
					else
						answers.add(i, pollardRho.getFactors(currentNumber, (System.currentTimeMillis() + timeSlice)));
				}
			} catch (TimeLimitExceededException e) {
				answers.add(null);
			}
		}

		
		print(answers);
	}
	
	public static void main2 (String[] args) {
		Random rnd = new Random();
		PollardRho pollardRho = new PollardRho();
		
		while (true) {
			BigInteger b1 = BigInteger.probablePrime(32, rnd);
			BigInteger b2 = BigInteger.probablePrime(32, rnd);
			BigInteger b3 = BigInteger.probablePrime(32, rnd);
			BigInteger b4 = BigInteger.probablePrime(32, rnd);
			BigInteger bi = b1.multiply(b2).multiply(b3).multiply(b4);
			bi = b4;
			LinkedList<BigInteger> factors;
			try {
				factors = pollardRho.getFactors(bi, System.currentTimeMillis() + 50000);
				if (!factors.isEmpty()) {
					BigInteger product = BigInteger.ONE;
					for (BigInteger factor : factors) {
						if (!factor.isProbablePrime(20)) {
							throw new RuntimeException(bi.toString() + " " + factor.toString());
						} else {
							product = product.multiply(factor);
							System.out.println("LEOELS");
						}
					}
					if(product.compareTo(bi) != 0) {
						throw new RuntimeException(bi.toString());
					}
				} else
					System.out.println("fail");
			} catch (TimeLimitExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<BigInteger> read() {
		ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		
		String line;
		try {
		line = br.readLine();
			while(line != null && !line.equals("")) {
				BigInteger tmp = new BigInteger(line);
				numbers.add(tmp);
				line = br.readLine();
			}
		} catch (IOException e) {
			
		}
		return numbers;
	}
	
	private static void print(ArrayList<LinkedList<BigInteger>> answers)
	{
		for(int i = 0; i < answers.size(); i++) {
			LinkedList<BigInteger> factors = answers.get(i);
			if(factors == null) {
				System.out.println("fail");
				System.out.println();
				continue;
			}
			
			for(BigInteger factor: factors)
				System.out.println(factor);
			
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