package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface AttendanceDAO {
    AttendanceEntity save(AttendanceEntity attendance);
    AttendanceEntity findById(Long id);
    Company findCompanyById(String id);
    Employee findEmployeeById(String employeeid);



}

