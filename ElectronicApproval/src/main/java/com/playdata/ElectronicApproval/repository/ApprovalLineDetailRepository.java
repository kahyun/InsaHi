package com.playdata.ElectronicApproval.repository;

import com.playdata.ElectronicApproval.entity.ApprovalLineDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineDetailRepository extends
    JpaRepository<ApprovalLineDetailEntity, String> {

}
