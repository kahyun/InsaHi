package com.playdata.AttendanceSalary.atdSalService.sal;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.AttendanceSalary.atdSalDto.sal.PositionResponseDTO;
import com.playdata.AttendanceSalary.atdSalRepository.sal.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * 회사 코드를 기준으로 직급 리스트 조회
     */
    public List<PositionResponseDTO> getPositionsByCompanyCode(String companyCode) {
        List<PositionEntity> positions = positionRepository.findByCompanyCode(companyCode);

        if (positions == null || positions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회사의 직급 정보가 존재하지 않습니다.");
        }

        return positions.stream()
                .map(PositionEntity::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 직급 추가
     */
    public PositionResponseDTO addPosition(String companyCode, PositionEntity positionEntity) {
        positionEntity.setCompanyCode(companyCode);
        PositionEntity savedPosition = positionRepository.save(positionEntity);
        return savedPosition.toDTO();
    }

    /**
     * 직급 수정
     */
    public PositionResponseDTO updatePosition(String companyCode, Long positionId, PositionEntity updatedPosition) {
        Optional<PositionEntity> existingPositionOpt = positionRepository.findById(positionId);

        if (existingPositionOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "직급을 찾을 수 없습니다.");
        }

        PositionEntity existingPosition = existingPositionOpt.get();

        if (!existingPosition.getCompanyCode().equals(companyCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회사의 직급 정보만 수정 가능합니다.");
        }

        existingPosition.setPositionName(updatedPosition.getPositionName());

        PositionEntity savedPosition = positionRepository.save(existingPosition);

        return savedPosition.toDTO();
    }

    /**
     * 직급 삭제
     */
    public void deletePosition(String companyCode, Long positionId) {
        Optional<PositionEntity> existingPositionOpt = positionRepository.findById(positionId);

        if (existingPositionOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "직급을 찾을 수 없습니다.");
        }

        PositionEntity existingPosition = existingPositionOpt.get();

        if (!existingPosition.getCompanyCode().equals(companyCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회사의 직급 정보만 삭제 가능합니다.");
        }

        positionRepository.delete(existingPosition);
    }
}
