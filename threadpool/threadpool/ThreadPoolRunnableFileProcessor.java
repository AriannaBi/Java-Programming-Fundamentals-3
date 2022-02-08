package linecount.threadpool;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import linecount.File;
import linecount.FileProcessor;
import linecount.parallel_common.FileProcessorRunnable;

//only with runnable and callable we whould have for example 1 milion of files so it means that we would have 
//1milion threads and this affects our system that becomes very unstable. 
//if number of threads exceed 500 or 600 the system can became very unstable because they can be active at the same time 
//and then will terminate after used all the CPU power for trying to switch btw threads and not for executing 
//the operations. moreover the memory is not enought.
//so in the callable if we have 200 files we will have 200 tasks and so 200 threads. 

//a threadpool separate the thread from the task.
//you have a pool of threads that is always active and the number of threads inside in very dinamically. 
//in the threadpool a thread can execute multiple tasks and the developer can submit a task to a threadpool
//without specify wich thread. the first thread that is not executing any other task can take another task out from the queue
//and execute it. 
//we can have only a few thread running in the system because of the coupled task (a thread can do more tasks sequenially)
//so the number of thread in the threadpool is less. 

//since we need to sumbit a task to a thread, we need to chose btw a callable or a runnable.

//a task execution framework is a concept that takes care of taking tasks and executing tasks using different threads,
//and depending on the implementation of the executor, you can define a diffeente behavior for the entity.

//a class executor is an Executor that contains methods for creating and managing a task execution framework.


//Threadpool can be cahced newCacheThreadpool() means that you can set an ideal number of thread inside threadpool and
//the number of thread depends on how many tasks are submitted to the threadpool, so this numnber will increase
//as the number of tasks increses and viceverse.

//newFixedThreadpool() which mantains the number of threadpool stable. 

//newScheduledThreadpool() submit command that will run periodically or with a specific delay

//newSingleThreadpool() which uses a single thread. 

/** A {@link FileProcessor} that computes the total line count 
 * through a {@link ThreadPoolExecutor} of fixed size. Each thread in the thread pool executes a 
 * {@link FileProcessorRunnable}, which in turn computes the line count of a single {@link File}.    
 * 
 */


public class ThreadPoolRunnableFileProcessor extends FileProcessor {
	private final ThreadPoolExecutor threadPool;

	public ThreadPoolRunnableFileProcessor(List<File> files) {
		this(files, Runtime.getRuntime().availableProcessors() + 1);//if we don't give the nThreads, as default has this		
	}

	public ThreadPoolRunnableFileProcessor(List<File> files, int nThreads) {
		super(files);

		// TODO  : Create a ThreadPoolExecutor that reuses
		//					a fixed number of threads (nThreads)
		//					and assign it to the threadPool field
		threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(nThreads);
	}
	
	@Override
	public long processAllFiles() throws InterruptedException {
		AtomicLong totalLineCount = new AtomicLong(0);

		// TOD 2 : Submit one FileProcessorRunnable per file to the thread pool
		for (File file : super.files) {
			threadPool.submit(new FileProcessorRunnable(file, totalLineCount));//we create a new runnable and call sumbit 
			//that allows to submit a task to a task execution framework. submit means from now on a task is take cared by 
			//the threadpool and we do that for each file.  
		}

		// TOD 3 : Shutdown the thread pool
		threadPool.shutdown(); // ask the threadpool to complete the execution of all the tasks that have been submitted 
		//but to not accept anymore tasks

		// TOD 4 : Block until all tasks have completed execution
		threadPool.awaitTermination(1, TimeUnit.DAYS); //ensure that threadpool ha enought time but it could be whatever
	
		// TOD 5 : Return the value encapsulated by totalLineCount
		return totalLineCount.get(); 
		//finally if the thread has been terminated (all tasks completed) which means that this atomic long in the counter
		//will be contained the total line counted of all the files. 

		//when run, every test has a unique id. here we have only 11 threads and each threads take care of multiple runnable. 
	
		//having one extra thread can run the code that otherwise can be idle, and so replace the task of a blocked thread
		//that's why we have the number of threads + 1.

		
	
	}	
}
