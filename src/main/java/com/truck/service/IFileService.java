package com.truck.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ming
 */
public interface IFileService {

    Map<String,String> upload(MultipartFile file, String path);

    String uploadCDN(MultipartFile file, String path) throws IOException;
}
