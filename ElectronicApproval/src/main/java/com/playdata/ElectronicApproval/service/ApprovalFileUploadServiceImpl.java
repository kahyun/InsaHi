package com.playdata.ElectronicApproval.service;

import com.playdata.ElectronicApproval.config.S3Properties;
import com.playdata.ElectronicApproval.dto.FileDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class ApprovalFileUploadServiceImpl implements ApprovalFileUploadService {


  @Value("${file.dir}")
  private String uploadPath;

  private final S3Client s3Client;
  private final S3Properties s3Properties;


  public String getUploadPath(String filename) {
    String dir = uploadPath.endsWith("/") ? uploadPath : uploadPath + "/";
    return dir + filename;
  }


  public List<FileDTO> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<FileDTO> fileDTOList = new ArrayList<>();

    for (MultipartFile multipartFile : multipartFiles) {
      if (!multipartFile.isEmpty()) {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        // S3 업로드
        PutObjectRequest putRequest = PutObjectRequest.builder()
            .bucket(s3Properties.getS3().getBucket())
            .key(storeFilename)
            .contentType(multipartFile.getContentType())
            .build();
        s3Client.putObject(putRequest, RequestBody.fromBytes(multipartFile.getBytes()));

        // 저장된 파일 정보를 DTO로 저장
        fileDTOList.add(
            new FileDTO(null, null, originalFilename, storeFilename));
      }
    }

    return fileDTOList;
  }

  private String createStoreFilename(String originalFilename) {
    int position = originalFilename.lastIndexOf(".");
    String ext = originalFilename.substring(position + 1);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

}