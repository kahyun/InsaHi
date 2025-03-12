package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.AllowanceEntity;

import java.util.List;
import java.util.Optional;


public interface AllowanceDao {

  AllowanceEntity saveAllowance(AllowanceEntity allowanceEntity);

  Optional<AllowanceEntity> findAllowanceById(Long id);

  void deleteAllowanceById(Long id);

  //김재희
  List<AllowanceEntity> findByPayStubId(Long payStubId);
}
