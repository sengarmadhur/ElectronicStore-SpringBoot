package com.ms.electronic.store.ElectronicStore.services.impl;

import com.ms.electronic.store.ElectronicStore.exceptions.BadApiRequest;
import com.ms.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String uploadImage(MultipartFile multipartFile, String path) throws IOException {

        String originalFileName = multipartFile.getOriginalFilename();
        logger.info("Filename : {} uploaded", multipartFile);
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString();
        String newFileNameWithExtension = newFileName + extension;
        String fullPathName = path + newFileNameWithExtension;
        logger.info("FullPathName : " + fullPathName);

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")
        || extension.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                boolean mkdirs = folder.mkdirs();
            }

            Files.copy(multipartFile.getInputStream(), Paths.get(fullPathName));
            return newFileNameWithExtension;
        } else {
            throw new BadApiRequest("File with this extension " + extension + " not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
