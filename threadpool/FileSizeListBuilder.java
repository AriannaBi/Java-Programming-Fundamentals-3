package linecount;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//
public class FileSizeListBuilder {

	/** 
	 * FileSizeListBuilder generates and stores a list of integer, each integer
	 * representing a file size. The output of this class (stored in OUTPUT_FILE_NAME)
	 * is used to guarantee a consistent input set (among each program run) and is used 
	 * by a {@link FileFactory}.   
	 */
	
	private final static int N_FILES = 10000;
	private final static int MIN_LINES_PER_FILE = 100;
	private final static int MAX_LINES_PER_FILE = 100000;

	
//	private final static int N_FILES = 10000000;
//	private final static int MIN_LINES_PER_FILE = 1000000;
//	private final static int MAX_LINES_PER_FILE = Integer.MAX_VALUE;

	
	private final static String OUTPUT_FILE_NAME = "filelist.ser";
	private final static String SIZE_FILE_NAME = "size.ser";

	private static void writeToFile(Object list, String fileName) {
		try {
			FileOutputStream fos= new FileOutputStream(fileName);
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
			fos.close();
		} catch (IOException e) {
		}
	}
	
	

	public static void main(String[] args) {
		List<Long> list = new ArrayList<>();
		Random random = new Random();

		long totalSize = 0;
		
		for (int i = 1; i <= N_FILES; i++) {
			Long size = new Long(MIN_LINES_PER_FILE + random.nextInt(MAX_LINES_PER_FILE - MIN_LINES_PER_FILE + 1));
			list.add(size);
			totalSize+=size;
			System.out.println("Files remaining: " + (N_FILES - i));
		}

		writeToFile(list, OUTPUT_FILE_NAME);
		writeToFile(totalSize, SIZE_FILE_NAME);

		System.out.println("Done!");

	}

}
