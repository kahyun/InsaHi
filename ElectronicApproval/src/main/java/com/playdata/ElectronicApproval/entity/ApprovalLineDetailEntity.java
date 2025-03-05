package com.playdata.ElectronicApproval.entity;

import com.playdata.User.company.entity.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "approvallinedetail")
public class ApprovalLineDetailEntity {

  @Id
  private String id;
  @OneToOne
  @JoinColumn(name = "approvalline_id")
  private ApprovalLineEntity approvalLine;
  @Enumerated(EnumType.STRING)

  private ApprovalStatus status;
  //결제일시는 updatedtime
  //private Date approvalDate;
  private String reason;

  private String companyCode;
}
