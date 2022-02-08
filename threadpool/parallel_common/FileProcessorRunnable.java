package linecount.parallel_common;

import java.util.concurrent.atomic.AtomicLong;

import linecount.File;

/**
 * FileProcessorRunnable is a {@link Runnable} which computes the line count of a single
 * {@link File}, passed in as input to its constructor. 
 * When the file line count is known, it stores the result by updating an {@link AtomicLong} (such that
 * the long can be updated concurrently by other threads).   
 *
 */

 //is a task that encapsulate some computation that must be done for a given file. 
 //takes two arguments, a file, and a counter. 
public class FileProcessorRunnable implements Runnable {
	private final File file;
	private final AtomicLong totalLineCounter;
		//here we need the atomiclogn because callable returns when it completes. 
		//both runnable and callable can encapsulate a task (unit of comutation that will be execute by a thread)
		//runnable is an interface and is very simple: the only method run cannot take any argument and return void.
		//but it has some limitation, 4 example that run does not return
		//so it is decided to add the callable.

		//the way to return is pass an atomic long and then update the state of the object. 
	public FileProcessorRunnable(File file, AtomicLong totalLineCounter) {
		this.file = file;
		this.totalLineCounter = totalLineCounter;
	}
	
	@Override
	public void run() {
		long lineCount = file.computeLineCount();
		totalLineCounter.getAndAdd(lineCount);
	}
}
