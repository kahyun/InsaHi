package com.playdata.HumanResourceManagement.department.feign.Controller;

import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import com.playdata.HumanResourceManagement.department.feign.service.DownloadPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 직급 및 직원 정보를 조회하는 API 컨트롤러
 */
@RestController
@RequestMapping("/api/{companyCode}/position")
@RequiredArgsConstructor
public class PositionController {

    private final DownloadPositionService positionService;

    /**
     * 회사 코드에 해당하는 직급 및 직원 데이터를 반환합니다.
     *
     * @param companyCode 회사 코드
     * @return 직급 및 직원 데이터
     */
    @GetMapping
    public DownloadPositionDTO getPositions(@PathVariable String companyCode) {
        return positionService.getPositionsWithEmployees(companyCode);
    }
}
