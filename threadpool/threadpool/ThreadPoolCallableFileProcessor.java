package linecount.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import linecount.File;
import linecount.FileProcessor;
import linecount.parallel_common.FileProcessorCallable;

/** A {@link FileProcessor} that computes the total line count 
 * through a {@link ThreadPoolExecutor} of fixed size. Each thread in the thread pool executes a 
 * {@link FileProcessorCallable}, which in turn computes the line count of a single {@link File}.    
 * 
 */


public class ThreadPoolCallableFileProcessor extends FileProcessor {
	private final ThreadPoolExecutor threadPool;

	public ThreadPoolCallableFileProcessor(List<File> files) {
		this(files, Runtime.getRuntime().availableProcessors() + 1);		
	}

	//here we can directly take the eturn value of the count, store it in a future and then looping on the list of 
	//future for obtian the final value. 
	
	public ThreadPoolCallableFileProcessor(List<File> files, int nThreads) {
		super(files);

		// TOD 1 : Create a ThreadPoolExecutor that reuses a fixed number of threads (nThreads)
		//					and assign it to the threadPool field
		threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(nThreads);
	}
	
	@Override
	public long processAllFiles() throws InterruptedException, ExecutionException {
		List<Future<Long>> results = new ArrayList<>(); 

		// TOD 2 : Submit one FileProcessorCallable per file to the thread pool
		//					Store the returned Future into the results List
		for (File file : super.files) {
			results.add(threadPool.submit(new FileProcessorCallable(file)));
			//submit method works both with runnable and callable, so we can pass both 
			//because a threadpool supports the use of callable, and the submit return a future
			//so we can store the future in the result list. (we don't need to convert the callable into runnable)

		}

		// TOD 3 : Shutdown the thread pool
		threadPool.shutdown();
		
		long totalLineCount = 0;
    
		// TOD 4 : Obtain the partial result from each thread and add it to the total
		//we can wait in the future list untill all results are availabled and return total
		for (Future<Long> result : results) {
			totalLineCount += result.get();
		}

		return totalLineCount;
	}

	//

	//in the first strategy we create one thread for each task, in the second we use a threadpool.
	// in general you should always prefer a threadpool to create threads for different tasks because if you have 
	//many tasks. This is because the first strategy will crash and will leave to a non stable system because it will have too much pressure.

	//it's true that if we have a low number of tasks then this stregy can be better then threadpool because with threadpool
	//we would have overheads of creating thredapol itself, but in general large scale application a threadpool is
	//always better.

	//in general is preferred a callable because in particular if you submit there is a sense to use callable that 
	//can return exception and result. the submit can return a future that is a placeholder for result

	//avoid creating one different thread for each task, and prefer callable to runnable. 

	//for simple application that don't create many tasks in paraller, creating one different threads per task and using
	//runnable than callable is preferred but only in this case (that number of tasks is not many).


}
