package com.playdata.attendanceSalary.atdSalRepository.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveUsageEntity;
import com.playdata.attendanceSalary.atdSalEntity.atd.LeaveApprovalStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualLeaveUsageRepository extends JpaRepository<AnnualLeaveUsageEntity, Long> {

  List<AnnualLeaveUsageEntity> findAllByCompanyCodeAndLeaveApprovalStatus(String companyCode,
      LeaveApprovalStatus approvalStatus);

  List<AnnualLeaveUsageEntity> findByEmployeeId(String employeeId);
}
