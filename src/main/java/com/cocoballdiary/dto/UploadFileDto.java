package com.cocoballdiary.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDto {

    private List<MultipartFile> files;
}
