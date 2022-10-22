package com.example.demoImageFileUploadAndDownloadSpringBoot.Controller;

import com.example.demoImageFileUploadAndDownloadSpringBoot.Entity.FileAttachment;
import com.example.demoImageFileUploadAndDownloadSpringBoot.Entity.FileResponse;
import com.example.demoImageFileUploadAndDownloadSpringBoot.Service.FileAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FileAttachmentController {

    @Autowired
    private FileAttachmentService fileAttachmentService;

    @PostMapping("/uploadOnlyFile")
    public FileResponse uploadOnlyFile(@RequestParam("file") MultipartFile file) throws Exception {
        FileAttachment fileAttachment=null;
        String downloadUrl = "";
            fileAttachment = fileAttachmentService.saveFileAttachment(file);
            downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileAttachment.getFileId())
                    .toUriString();
            return new FileResponse(fileAttachment.getFileName(),
                    downloadUrl,
                    file.getContentType(),
                    file.getSize());
    }


/*this method for multiple files upload */
    @PostMapping("/uploadMultipleFile")
    public List<FileResponse> uploadMultipleFile(@RequestParam("file") MultipartFile[] file) throws Exception {
        List<FileResponse> fileResponseList=new ArrayList<>();
        for (MultipartFile arr : file) {
            FileResponse fileResponse=new FileResponse();
            FileAttachment fileAttachment = null;
            fileAttachment = fileAttachmentService.saveFileAttachment(arr);

            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileAttachment.getFileId())
                    .toUriString();

            fileResponse.setFileName(fileAttachment.getFileName());
            fileResponse.setDownloadUrl(downloadURL);
            fileResponse.setFileType(fileAttachment.getFileType());
            fileResponse.setFileSize(arr.getSize());
            fileResponseList.add(fileResponse);

        }
        return fileResponseList;
        }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId) throws Exception {
        FileAttachment fileAttachment=null;
        fileAttachment=fileAttachmentService.getFileAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileAttachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileAttachment.getFileName() + "\"")
                .body(new ByteArrayResource(fileAttachment.getDataSize()));
    }

    /* This method use for getting all data from Data base with download URL  */
    @GetMapping("/downloadAll")
    public List<FileResponse> downloadAllData() throws Exception {
        List<FileAttachment> fileAttachmentList = fileAttachmentService.getAllAttachment();
        List<FileResponse> fileResponseList=new ArrayList<>();

        for (FileAttachment fileAttachment : fileAttachmentList) {
            FileResponse fileResponse=new FileResponse();

            String downloadURL=ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(fileAttachment.getFileId())
                    .toUriString();

            fileResponse.setFileName(fileAttachment.getFileName());
            fileResponse.setDownloadUrl(downloadURL);
            fileResponse.setFileType(fileAttachment.getFileType());
            fileResponseList.add(fileResponse);
        }
        return fileResponseList;
    }

}
