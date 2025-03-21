package com.playdata.AttendanceSalary.ahrmClient.service;

import com.playdata.AttendanceSalary.ahrmClient.dto.AttSendPositionDTO;
import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.AttendanceSalary.atdSalRepository.sal.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 직급 관련 비즈니스 로직 서비스
 */
@Service
@RequiredArgsConstructor
public class AttPositionService {

    private final PositionRepository positionRepository;

    /**
     * 회사 코드 기준 직급 리스트 조회
     */
    public List<AttSendPositionDTO> getPositionsByCompanyCode(String companyCode) {
        return positionRepository.findByCompanyCode(companyCode).stream()
                .map(AttSendPositionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 회사 코드 및 직급 ID 기준 단일 직급 조회
     */
    public AttSendPositionDTO getPositionById(String companyCode, Long id) {
        PositionEntity position = positionRepository.findByCompanyCodeAndPositionId(companyCode, id)
                .orElseThrow(() -> new EntityNotFoundException("직급을 찾을 수 없습니다."));
        return AttSendPositionDTO.fromEntity(position);
    }
}
