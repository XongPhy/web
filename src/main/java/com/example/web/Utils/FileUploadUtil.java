package com.example.web.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDir,String fileName, MultipartFile multipartFile) throws IOException {
		Path upLoadPath = Paths.get(uploadDir);
		if(!Files.exists(upLoadPath)) {
			Files.createDirectories(upLoadPath);
		}
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = upLoadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
			
		}catch (IOException exception) {
			// TODO: handle exception
			throw new IOException("Could not save img : " + fileName,exception);
		}
	}
}
