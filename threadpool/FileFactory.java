package linecount;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

//takes a file (filelist.ser) contains the number of lines precomputed.
public class FileFactory {
	
	/** FileFactory creates the initial list of files to be processed by a {@link FileProcessor}. 
	 * The purpose of this class is to recreate exactly the same set of files 
	 * at each run of the application. This allows to compare the correctness of 
	 * the program using different {@link FileProcessor}, since the total line count should be
	 * equal.    
	 */

	private final static int N_FILES = 10000;
	//private final static int N_FILES = 10000000;
	
	private final static String FILE_SIZE_INPUT_FILE = "filelist.ser";
	private final static String TOTAL_SIZE_INPUT_FILE = "size.ser";

	public static List<File> createFiles() {
		return createFiles(N_FILES);
	}

	private static Object readObject(String fileName) {
		FileInputStream fis;
		Object object = null;

		try {			
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			object = ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
		}
		
		return object;
	}
	
	
	@SuppressWarnings("unchecked")
	private static ArrayList<Long> readFileSizes() {
		return (ArrayList<Long>) readObject(FILE_SIZE_INPUT_FILE);
	}
	
	private static Long readTotalFileSize() {
		return (Long) readObject(TOTAL_SIZE_INPUT_FILE);
	}
	
	
	public static Long getTotalSize() {
		return readTotalFileSize();
	}
	

	public static List<File> createFiles(int nItems) {
		List<File> items = new ArrayList<>();		
		List<Long> fileSizes = readFileSizes();
		
		int realNumFiles = nItems <= fileSizes.size() ? nItems : fileSizes.size();  
		
		for (int i = 0; i < realNumFiles; i++) {
			items.add(new File(i, fileSizes.get(i)));
		}
		
		return items;
	}
	
	
	
	
	
}
