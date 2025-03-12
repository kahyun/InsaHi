package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveEntity;
import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveUsageEntity;
import com.playdata.attendanceSalary.atdSalEntity.atd.LeaveApprovalStatus;
import java.util.List;
import java.util.Optional;

public interface AnnualLeaveUsageDAO {

  List<AnnualLeaveUsageEntity> findAllByCompanyCodeAndLeaveApprovalStatus(String companyCode,
      LeaveApprovalStatus approvalStatus);

  Optional<AnnualLeaveUsageEntity> findById(long id);

  void save(AnnualLeaveUsageEntity leave);

  List<AnnualLeaveUsageEntity> findByEmployeeId(String employeeId);
}
