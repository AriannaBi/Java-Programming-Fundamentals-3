package linecount.thread;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import linecount.File;
import linecount.FileProcessor;
import linecount.parallel_common.FileProcessorRunnable;
//this implementation does not allowed to throws exception or return value outside. 

/** A {@link FileProcessor} that computes the total line count 
 * by creating a new thread for each file to be processed. Each thread executes a 
 * single {@link FileProcessorRunnable}, which in turn computes the line count of a single {@link File}.    
 * 
 */

 //create a file processor that computes the count line with threads.
 //one thread for each file to be computed.
public class ThreadFileProcessorRunnable extends FileProcessor {

	public ThreadFileProcessorRunnable(List<File> files) {
		super(files);
	}

	@Override
	public long processAllFiles() throws InterruptedException {
		AtomicLong totalLineCount = new AtomicLong(0);
		
		// TODO 1 : Create and start one thread per file passing a FileProcessorRunnable as argument
		Thread[] threads = new Thread[super.files.size()];//one thread for each file
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			threads[i] = new Thread(new FileProcessorRunnable(file, totalLineCount));
			threads[i].start();
		}

		// TODO 2 : Use the join method to ensure that all files have been processed
		for(Thread thread: threads) {
			thread.join(); //check whether threads terminated. if threads are not terminate, joitn will wait until
			//all have terminatede. 
		}
	
		// TODO 3 : Return the value encapsulated by totalLineCount
		return totalLineCount.get();
	}
}
