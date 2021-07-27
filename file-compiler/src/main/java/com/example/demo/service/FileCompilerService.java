package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class FileCompilerService {

	public void fileCompiler(String fileName) throws InterruptedException {
		String fileNameWithoutExtension = getFileNameWithoutExtension(fileName);
		String[] compileCommandsList = {"gcc", "-o", fileNameWithoutExtension, fileName};
		String[] executionCommandsList = {"./"+fileNameWithoutExtension};
		try {
			
			ProcessBuilder processBuilder = new ProcessBuilder(compileCommandsList).redirectErrorStream(true).redirectInput(ProcessBuilder.Redirect.INHERIT) 
                    .redirectOutput(ProcessBuilder.Redirect.to(new File("/Users/suryajeripothula/Documents/myCPrograms/myCPrograms/output.txt"))) 
                    .redirectError(ProcessBuilder.Redirect.to(new File("/Users/suryajeripothula/Documents/myCPrograms/myCPrograms/error.txt")));
			processBuilder.command(compileCommandsList);
			processBuilder.directory(new File("/Users/suryajeripothula/Documents/myCPrograms/myCPrograms/"));
			
			Process compileProcess = processBuilder.start();
			int errorCode = compileProcess.waitFor();
			if(errorCode == 0) {
				processBuilder.command(executionCommandsList);
				Process executeProcess = processBuilder.start();
				int executeProcessErrorCode = executeProcess.waitFor();
				if(executeProcessErrorCode == 0) {
					String dataMsg = reader(executeProcess.getInputStream());
				} else {
					System.out.println("error");
				}
			} else {
				System.out.println("This is a compilation error");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String reader(InputStream input) {
	    StringBuilder outDat = new StringBuilder();
	    try (InputStreamReader inputReader = new InputStreamReader(input, StandardCharsets.UTF_8);
	        BufferedReader bufferedReader = new BufferedReader(inputReader)) {
	      String line;
	      while ((line = bufferedReader.readLine()) != null) {
	        outDat.append(line);
	      }
	    } catch (IOException e) {
	    	System.out.println("New IO Exception");
	    }
	    return outDat.toString();
	 }
	
	private static String getFileNameWithoutExtension(String fileName) {
		   int dotIndex = fileName.lastIndexOf('.');
		   return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
		 }
	
}


