package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileUploadService;

@RestController  
public class FileUploadController   
{  

	@Autowired
	FileUploadService fileUploadService;
	
	
	@GetMapping("/isAlive")  
	public String isAlive()   
	{  
		return "true"; 
	}
	
	@PostMapping("/postFile")
	public void fileUpload(@RequestParam("file") MultipartFile file) {
		
		try {
			fileUploadService.storeFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
