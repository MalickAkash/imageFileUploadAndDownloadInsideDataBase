package com.example.demoImageFileUploadAndDownloadSpringBoot.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class FileAttachment {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "file_id", nullable = false)
    private String fileId;
    private String fileName;
    private  String fileType;
    @Lob
    private byte [] dataSize;

    public FileAttachment(String fileName, String fileType, byte[] dataSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.dataSize = dataSize;
    }
}
