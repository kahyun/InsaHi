package com.playdata.ElectronicApproval.repository;

import com.playdata.ElectronicApproval.entity.ApprovalFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalFileRepository extends JpaRepository<ApprovalFileEntity, String> {

}
