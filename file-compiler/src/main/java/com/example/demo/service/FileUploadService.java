package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileUploadService {

	@Autowired
	FileCompilerService fileCompilerService;
	
	String fileStoragePath = "/Users/suryajeripothula/Documents/myCPrograms/myCPrograms/";
	
	public void storeFile(MultipartFile file) throws Exception {
		try {
			String fileName = file.getOriginalFilename();
			
            InputStream is = file.getInputStream();

            Files.copy(is, Paths.get(fileStoragePath + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            
            fileCompilerService.fileCompiler(fileName);
            
		} catch (IOException e) {
            String msg = String.format("Failed to store file %f", file.getName());

            throw new Exception(msg, e);
		}
    }

	
}
