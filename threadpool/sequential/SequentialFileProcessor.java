package linecount.sequential;

import java.util.List;

import linecount.File;
import linecount.FileProcessor;

/** A {@link FileProcessor} that computes the total line count 
 * sequentially.  
 * 
 */

//exists only a single thread main 

//eache thread should take a file as input, compute the line count and then tells somebody which was the count and terminating
//before create a thread, we need a task of the thread passed in constructor 
public class SequentialFileProcessor extends FileProcessor {

	
	public SequentialFileProcessor(List<File> files) {
		super(files);		
	}

	@Override
	public long processAllFiles() {
		long totalLineCount = 0;
		
		for (File file: super.files) {
			totalLineCount += file.computeLineCount();
		}
		
		return totalLineCount;
	}

}
