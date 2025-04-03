package com.playdata.HumanResourceManagement.department.feign;

import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "att-Feign", url = "${url.service.att}")
public interface AttFeignClient {

    /**
     * 회사 코드에 대한 직급 정보를 조회합니다.
     *
     * @param companyCode 회사 코드
     * @return 직급 정보 목록
     */
    @GetMapping("/positions/{companyCode}")
    PositionDownloadDTO getPositionDownloadDTO(@PathVariable String companyCode);

    /**
     * 직급 정보를 추가합니다.
     *
     * @param companyCode 회사 코드
     * @param positionDTO 직급 정보
     * @return 추가된 직급 정보
     */
    @PostMapping("/positions/{companyCode}")
    PositionDownloadDTO addPosition(@PathVariable String companyCode, @RequestBody PositionDownloadDTO positionDTO);

    /**
     * 직급을 삭제합니다.
     *
     * @param companyCode 회사 코드
     * @param positionName 직급명
     * @return 삭제 성공 여부
     */
    @DeleteMapping("/positions/{companyCode}/{positionName}")
    boolean deletePosition(@PathVariable String companyCode, @PathVariable String positionName);

    /**
     * 직급 ID에 따른 직급명을 조회합니다.
     *
     * @param positionSalaryId 직급 ID
     * @return 직급명
     */
    @GetMapping("/positions/name/{positionSalaryId}")
    String getPositionName(@PathVariable Long positionSalaryId);
}
