package linecount.parallel_common;

import java.util.concurrent.Callable;
import linecount.File;

/**
 * FileProcessorCallable is a {@link Callable} which computes the line count of a single
 * {@link File}, passed in as input to its constructor. 
 *
 */

 //has 2 more functionality with respect to runnable:
 //return something in its call() method, and call() can throw exception (here we don't need).
public class FileProcessorCallable implements Callable<Long> {

	private final File file;
	
	public FileProcessorCallable(File file) {
		this.file = file;
	}
	
	@Override
	public Long call() { //equal to run of runnable.
		return file.computeLineCount();
	}

}
