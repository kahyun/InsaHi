package com.playdata.attendanceSalary.atdSalController.sal;

import com.playdata.attendanceSalary.atdSalDto.sal.PositionSalaryStepResponseDTO;
import com.playdata.attendanceSalary.atdSalService.sal.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @PostMapping("/position-insert")
    public PositionSalaryStepResponseDTO positionSalaryStepInsert(@RequestBody PositionSalaryStepResponseDTO responseDTO) {
        return salaryService.insertPosition(responseDTO);

    }
}
