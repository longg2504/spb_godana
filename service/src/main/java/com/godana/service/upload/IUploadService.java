package com.godana.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUploadService {
    Map uploadImage(MultipartFile multipartFile, Map params) throws IOException;

    List<Map> uploadImages(List<MultipartFile> multipartFiles, Map options) throws IOException;

    Map destroyImage(String publicId, Map params) throws IOException;

}
