package linecount;

import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class FileProcessor {
	
	/**
	 *  FileProcesser computes the total line count of a set of files, passed 
	 *  as input to the constructor. 
	 *  
	 *  The actual implementation on how to compute the total line count is
	 *  delegated to subclasses, which must implement method countTotalLineCount(). 
	 **/
	
	protected final List<File> files;
	
	public FileProcessor(List<File> files) {
		this.files = files;
	}
	
	
	/**
	 *  Process all files, computing the total line count.
	 *  This method must be implemented by a subclass to compute the total line count 
	 *  of all files stored in the "files" field.  
	 * @return the total line count of all files  
	 * @throws ExecutionException 
	 */
	
	public abstract long processAllFiles() throws InterruptedException, ExecutionException;

}
