package linecount.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import linecount.File;
import linecount.FileProcessor;
import linecount.parallel_common.FileProcessorCallable;
//solves limitation of rannable, so it can return value, can throws exception. 
//the only issue is that if we want to create a thread we need to convert the callable to runnable. 

/** A {@link FileProcessor} that computes the total line count 
 * by creating a new thread for each file to be processed. Each thread executes a 
 * single {@link FileProcessorCallable}, which in turn computes the line count of a single {@link File}.    
 * 
 */

public class ThreadFileProcessorCallable extends FileProcessor {

	public ThreadFileProcessorCallable(List<File> files) {
		super(files);
	}

	@Override
	public long processAllFiles() throws InterruptedException, ExecutionException {
		List<Future<Long>> futures = new LinkedList<>();
		//a future is a placeholder for a result that might be availabled or might not yes. 
		//it provides methods to get the result and also to wait if there is no the result yet.
		//so instead of join we will check if the result is availabled in the future. 


		// TODO 1 : Create and start one thread per file passing a FutureTask as argument (and not a callable).
		//The FutureTask must be created from a FileProcessorCallable.
		for(File file : files) { 
			FutureTask<Long> future = new FutureTask(new FileProcessorCallable(file)); //futureTask convert a callable to a runnable
			//so that we can pass it to the new thread. 
			//the future object can encapsulate the computation that will be made availabled by the callable of futuretask.
			futures.add(future); //add because after we will check if the result is availabled. 
			new Thread(future).start();
		}
        
        long totalCount = 0;
        
		// TODO 2 : Obtain the partial result from each thread and add it to the total by waiting until the result in each future is ready.

		for(Future<Long> future : futures) {
			totalCount += future.get();//check whether the result is encapsulated and already availabled.
			//if it is it returns the value otherwise the thread will wait. 
		}
        return totalCount;
	}


}
