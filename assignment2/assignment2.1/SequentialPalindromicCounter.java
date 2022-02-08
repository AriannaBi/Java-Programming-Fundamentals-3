package assignment2;

public class SequentialPalindromicCounter implements Counter {

	public int count(int[] nums) {
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (Palindromic.isPalindromic(nums[i])) {
				count++;
			}
		}
		return count;
	}
}
