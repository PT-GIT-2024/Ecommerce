package com.example.Ecommerce.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //Get file name of current / original file / e.g. prashant.jpg
        String originalFileName = file.getOriginalFilename();

        //Generate a unique file name
        String randomId = UUID.randomUUID().toString(); //1234
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.'))); //1234 + .jpg
        String filePath = path + File.separator + fileName; // "/images" + "/" + "1234.jpg" -> images/1234.jpg

        //Check if path exist or create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //returning the filename
        return fileName;
    }
}
