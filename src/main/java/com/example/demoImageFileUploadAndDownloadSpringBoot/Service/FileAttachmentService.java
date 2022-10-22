package com.example.demoImageFileUploadAndDownloadSpringBoot.Service;

import com.example.demoImageFileUploadAndDownloadSpringBoot.Entity.FileAttachment;
import com.example.demoImageFileUploadAndDownloadSpringBoot.Repository.FileAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileAttachmentService {

    @Autowired
    private FileAttachmentRepository fileAttachmentRepository;

    public FileAttachment saveFileAttachment(MultipartFile file) throws Exception {
        String filename= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(filename.contains("..")){
                throw new Exception("Filename Contents invalid path sequence"+ filename);
            }

            FileAttachment fileAttachment =new FileAttachment(filename,
                    file.getContentType(),
                    file.getBytes());
            return fileAttachmentRepository.save(fileAttachment);

        }
        catch (Exception e)
        {
            throw new Exception("Could not save file."+ filename);
        }
    }


    public FileAttachment getFileAttachment(String fileId) throws Exception {
        return fileAttachmentRepository.findById(fileId)
                .orElseThrow(()->new Exception("File not found with Id "+ fileId));
    }

    public List<FileAttachment> getAllAttachment() throws Exception {
        List<FileAttachment> fileAttachmentList = fileAttachmentRepository.findAll();
        if (fileAttachmentList.isEmpty()) {
            throw new Exception("Data Base is Empty, there has no Data inside Data Base.");
        }
        return fileAttachmentList;
    }
}
