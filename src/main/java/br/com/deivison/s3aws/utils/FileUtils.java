package br.com.deivison.s3aws.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	
	private FileUtils() {}
	
	public static void copyInputStringToFile(InputStream input, File fileSaveDir) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileSaveDir);

			fileOutputStream.write(input.readAllBytes());
			fileOutputStream.flush();
			
			// close
			fileOutputStream.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
