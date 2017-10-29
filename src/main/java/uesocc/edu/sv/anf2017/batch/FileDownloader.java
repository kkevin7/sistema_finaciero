
package com.uesocc.batch;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 *
 * @author jssbbonilla
 */
public class FileDownloader {

		OutputStream outputStream = null;
        
        public void uploader(InputStream inputStream){
            
            
	try {
		// read this file into InputStream
		

		// write the inputStream to a FileOutputStream
		outputStream =
                    new FileOutputStream(new File("./solicitudes.csv"));

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

		System.out.println("Done!");

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
        
        }

    }

