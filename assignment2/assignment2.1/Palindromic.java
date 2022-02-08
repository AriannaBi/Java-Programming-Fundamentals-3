package assignment2;

public final class Palindromic {

	public static boolean isPalindromic(int n) {
		int divisor = 1; 
		while (n / divisor >= 10) {
			divisor *= 10; 
		}
		while (n != 0) { 
			int leading = n / divisor;  
			int trailing = n % 10; 

			if (leading != trailing) {   
				return false; 
			}
			n = (n % divisor) / 10; 
			divisor = divisor / 100; 
		} 
		return true; 
	}
}
