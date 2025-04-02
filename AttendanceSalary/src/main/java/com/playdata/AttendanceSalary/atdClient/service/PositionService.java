package com.playdata.AttendanceSalary.atdClient.service;

import com.playdata.AttendanceSalary.atdClient.HrmFeignClient;
import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 직급 데이터를 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class PositionService {

    private final HrmFeignClient hrmFeignClient;

    /**
     * ATT 시스템에서 직급 데이터를 가져와 HRM 시스템으로 전송
     * @param companyCode 회사 코드
     * @return 직급 데이터 리스트
     */
    public List<PositionSendDTO> fetchAndSendPositionDataToHRM(String companyCode) {
        List<PositionSendDTO> positionList = getPositions(companyCode);
        sendPositionsToHRMSystem(companyCode, positionList);
        return positionList;
    }

    /**
     * 회사 코드에 맞는 직급 데이터를 가져오는 메서드
     * @param companyCode 회사 코드
     * @return 직급 리스트
     */
    public List<PositionSendDTO> getPositions(String companyCode) {
        // ATT에서 직급 데이터를 조회하는 메서드 (예: FeignClient 사용)
        return hrmFeignClient.getPositions(companyCode);
    }

    /**
     * 직급 데이터를 HRM 시스템으로 전송
     * @param companyCode 회사 코드
     * @param positionDTOs 직급 데이터 리스트
     */
    public void sendPositionsToHRMSystem(String companyCode, List<PositionSendDTO> positionDTOs) {
        // 직급 데이터를 HRM 시스템으로 전송 (HRM의 직급 관련 API 호출)
        hrmFeignClient.sendPositionsToHRM(companyCode, positionDTOs);
    }
}
