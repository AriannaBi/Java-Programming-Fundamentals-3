package linecount;


public class File {

	//we have a very large set of very large files and the goal is to build a component that is able to 
	//compute a total line count.
	//so it can open the file, read them, compute the number of line and sum up the result of all the files.

	//you can submit tasks to thread pool, they can be either runnable or callable.
	/** A class representing a file 
	 **/
	
	private final int id;
	private final long lineCount;
	//private static int COMP_PER_LINE = 1;
	private static int COMP_PER_LINE = 100;
	
	public File (int id, long lineCount) {
		this.id = id; 
		this.lineCount = lineCount;
	}
	
	public int getID() {
		return id;
	}
		
	/** Simulates the process of computing the number of lines
	 * that compose the file. 
	 * While the number of lines is known already (i.e., stored in lineCount), 
	 * this method performs some useless computation before returning the count, to 
	 * account a realistic processing time that this computation would take.  
	 * 
	 * @return the number of lines composing the file. 
	 */
	
	 //when the file processor has to count the number of line, whill call this method 
	public long computeLineCount() {
		System.out.format("Thread: %s - Start processing file: %d\n", Thread.currentThread().getName(), id);
		
		long count = lineCount * COMP_PER_LINE;//simulate line counting 
		while (--count >= 0) ;
		
		System.out.format("Thread: %s - End processing file: %d\n", Thread.currentThread().getName(), id);
		
		return lineCount;				
	}
	
}
