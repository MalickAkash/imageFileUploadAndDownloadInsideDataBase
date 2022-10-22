package com.example.demoImageFileUploadAndDownloadSpringBoot.Repository;

import com.example.demoImageFileUploadAndDownloadSpringBoot.Entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment,String> {

}
