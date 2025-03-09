package com.playdata.ElectronicApproval.controller;

import com.playdata.ElectronicApproval.dto.RequestApprovalFileDTO;
import com.playdata.ElectronicApproval.service.ApprovalService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequiredArgsConstructor
public class ApprovalController {

  private final ApprovalService approvalService;

  @PostMapping("/submit")
  public void submitApproval(
      @RequestParam("employeeId") String employeeId,
      @RequestParam("companyCode") String companyCode,
      @RequestBody RequestApprovalFileDTO dto,
      @RequestParam("approvaers") List<String> approvaers,
      @RequestParam("referencedIds") List<String> referrers
  ) {
    approvalService.submitApproval(employeeId, companyCode, dto, approvaers, referrers);
  }

  @GetMapping("/permit")
  public void permitApproval(@RequestParam("lineId") String lineId,
      @RequestParam("approveOrNot") String approveOrNot,
      @RequestParam("reason") String reason) {
    approvalService.approveUpdateStatus(lineId, approveOrNot, reason);
  }

}
