package com.example.demoImageFileUploadAndDownloadSpringBoot.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String fileName;
    private String downloadUrl;
    private String fileType;
    private Long fileSize;
}