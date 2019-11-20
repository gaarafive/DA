package com.example.karaoke.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	public boolean UploadImage(MultipartFile file, String url) {
        File uploadRootDir = new File(url);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        String name = file.getOriginalFilename();

        if (name != null && name.length() > 0) {
            try {
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
            	return false;
            }
        }
		return true;
	}
}
