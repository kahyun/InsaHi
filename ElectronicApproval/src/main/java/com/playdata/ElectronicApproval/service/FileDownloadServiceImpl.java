package com.playdata.ElectronicApproval.service;

import com.playdata.ElectronicApproval.common.publicEntity.FileEntity;
import com.playdata.ElectronicApproval.config.S3Properties;
import com.playdata.ElectronicApproval.dao.FileDAO;
import com.playdata.ElectronicApproval.dto.FileDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDownloadServiceImpl implements FileDownloadService {

  private final FileDAO fileDao;
  @Value("${file.dir}")
  private String uploadDir;
  private final S3Client s3Client;
  private final S3Properties s3Properties;


  // 단일 파일 다운로드 처리
  public ResponseEntity<Resource> downloadFile(String fileId) {
    // 파일 엔티티 조회
    FileEntity fileEntity = fileDao.findById(Long.valueOf(fileId))
        .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));

    String key = fileEntity.getStoreFilename();

    try {
      GetObjectRequest getRequest = GetObjectRequest.builder()
          .bucket(s3Properties.getS3().getBucket())
          .key(key)
          .build();

      ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getRequest);
      ByteArrayResource resource = new ByteArrayResource(objectBytes.asByteArray());

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + fileEntity.getOriginalFilename() + "\"")
          .body(resource);

    } catch (S3Exception e) {
      throw new RuntimeException("파일 다운로드 실패: " + e.getMessage(), e);
    }
  }


  // 파일 다운로드를 위한 ResponseEntity 생성
  private ResponseEntity<Resource> createFileDownloadResponse(Resource resource,
      String originalFilename) {
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + originalFilename + "\"")
        .body(resource);
  }

  // 결재 문서 ID로 해당 파일 정보 조회
  public List<FileDTO> loadAllFiles(String approvalFileId) {
    return fileDao.findAllByApprovalFileId(approvalFileId).stream()
        .map(file -> new FileDTO(file.getId(), file.getApprovalFileId(), file.getOriginalFilename(),
            file.getStoreFilename()))
        .collect(Collectors.toList());
  }

}