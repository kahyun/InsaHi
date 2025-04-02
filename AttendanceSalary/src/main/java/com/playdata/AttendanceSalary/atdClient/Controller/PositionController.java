package com.playdata.AttendanceSalary.atdClient.Controller;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.AttendanceSalary.atdClient.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 직급 데이터를 HRM 시스템으로 전송
     * @param companyCode 회사 코드
     */
    @PostMapping("/positions")
    public void sendPositionsToHRM(@RequestParam String companyCode) {
        List<PositionSendDTO> positionDTOs = positionService.fetchAndSendPositionDataToHRM(companyCode);
        // 직급 데이터 전송 후 추가 작업 (예: 로그 기록) 가능
    }

    /**
     * 회사 코드에 맞는 직급 데이터를 가져오는 메서드
     * @param companyCode 회사 코드
     * @return 회사 코드에 해당하는 직급 데이터 리스트
     */
    @GetMapping("/positions")
    public List<PositionSendDTO> getPositions(@RequestParam String companyCode) {
        return positionService.getPositions(companyCode);  // PositionSendDTO 리스트 반환
    }

    /**
     * HRM 시스템으로 직급 데이터를 전송하는 메서드
     * @param companyCode 회사 코드
     * @param positionDTOs 전송할 직급 정보 리스트
     */
    @PostMapping("/send-to-hrm")
    public void sendPositionsToHRMSystem(@RequestParam String companyCode,
                                         @RequestBody List<PositionSendDTO> positionDTOs) {
        positionService.sendPositionsToHRMSystem(companyCode, positionDTOs);
    }
}
