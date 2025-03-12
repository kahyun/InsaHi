package com.playdata.attendanceSalary.atdSalService.atd;

import com.playdata.attendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import com.playdata.attendanceSalary.atdSalDto.atd.AnnualLeaveRequestDTO;
import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveUsageEntity;
import java.util.List;

public interface AnnualLeaveService {

  void submit(String employeeId, String companyCode, AnnualLeaveRequestDTO dto);

  List<AnnualLeaveUsageEntity> findAllByEmployeeId(String employeeId);

  List<AnnualLeaveUsageEntity> findAllByCompanyCode(String companyCode, String status);

  void approveLeave(AnnualLeaveRequestDTO requestDTO);

  void rejectLeave(AnnualLeaveRequestDTO requestDTO);

  void approveAdditionalLeave(AnnualLeaveRequestDTO requestDTO);

  void executeLeaveGrant();
}
