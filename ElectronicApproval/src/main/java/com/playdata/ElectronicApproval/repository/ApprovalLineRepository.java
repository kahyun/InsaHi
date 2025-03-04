package com.playdata.ElectronicApproval.repository;

import com.playdata.ElectronicApproval.entity.ApprovalLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineRepository extends JpaRepository<ApprovalLineEntity, String> {

}
