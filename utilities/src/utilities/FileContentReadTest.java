package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileContentReadTest {

	public static void main(String[] args) {
		File fileIn;
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader bfr;
		PrintWriter pw;
		String currentLine;
		String sourceFolder;
		String sourceFile;
		String targetFolder;
		int fileCount;
		int lineCount;
		
		fis = null;
		bfr = null;
		fileCount = 0;
		lineCount = 0;
		
		sourceFolder = args[0];
		sourceFile = args[1];
		targetFolder = args[2] + "\\";
		try {
			fileIn = new File(sourceFolder + sourceFile);
			fis = new FileInputStream(fileIn);
			isr = new InputStreamReader(fis);
			bfr = new BufferedReader(isr);

			pw = createNewPrintWriter(targetFolder + sourceFile + ".part", fileCount);

			currentLine = bfr.readLine();
			while (currentLine != null) {
				pw.write(currentLine + System.lineSeparator());
				lineCount++;
				if (lineCount > 1000000) {
					System.out.println("Writing file -> " + fileCount);
					pw.flush();
					pw.close();
					
					lineCount = 0;
					fileCount++;
					
					pw = createNewPrintWriter(targetFolder + "debug.out.part", fileCount);
				}
				currentLine = bfr.readLine();
			}
			System.out.println("Finished writing files");
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// Ignore
				}
			}
			if (bfr != null) {
				try {
					bfr.close();
				} catch (Exception e) {
					// Ignore
				}
			}
		}	
	}

	private static PrintWriter createNewPrintWriter(String fileName, int fileCount) throws IOException {
		File fileOut;
		FileWriter fw;
		BufferedWriter bw;
		PrintWriter pw;

		fileOut = new File(fileName + fileCount);
		fw = new FileWriter(fileOut, true);
		bw = new BufferedWriter(fw);
		pw = new PrintWriter(bw);
		
		return pw;
	}
}
