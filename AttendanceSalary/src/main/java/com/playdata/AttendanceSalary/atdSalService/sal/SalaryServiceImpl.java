package com.playdata.attendanceSalary.atdSalService.sal;


import com.playdata.attendanceSalary.atdSalDao.sal.*;
import com.playdata.attendanceSalary.atdSalDto.sal.*;
import com.playdata.attendanceSalary.atdSalEntity.sal.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    private final PositionDao positionDao;
    private final PositionSalaryDao positionSalaryDao;
    private final ModelMapper modelMapper;
    private final AllowanceDao allowanceDao;
    private final DeductionDao deductionDao;
    private final PayStubDao payStubDao;
    private final SalaryDao salaryDao;

    @Override
    public PayStubResponseDTO calculateEmployeeSalary(Long employeeId) {
        return null;
    }

    ///  Position 서비스
    // 회사에서 직급 추가
    @Override
    public PositionResponseDTO insertPosition(PositionResponseDTO requestDTO, String CompanyCode) {
        PositionEntity position = requestDTO.toEntity();
        PositionEntity pp = positionDao.savePosition(position);
        return modelMapper.map(pp, PositionResponseDTO.class);
    }
    // 회사에서 직급 수정
    @Override
    public void updatePosition(PositionResponseDTO requestDTO) {
        PositionEntity position = requestDTO.toEntity();
        positionDao.savePosition(position);
    }
    // 회사에서 직급 삭제
    @Override
    public void deletePosition(PositionResponseDTO requestDTO) {
        PositionEntity position = requestDTO.toEntity();
        positionDao.deletePosition(position);
    }
    @Override
    public PositionResponseDTO findPosition(Long positionId) {
        return modelMapper.map(positionDao.findById(positionId), PositionResponseDTO.class);
    }
    /// positionSalaryStep 서비스
    @Override
    public PositionSalaryStepResponseDTO insertPositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        PositionSalaryStepEntity ps = positionSalaryDao.savePositionSalaryStep(pss);
        PositionSalaryStepResponseDTO dto = modelMapper.map(ps, PositionSalaryStepResponseDTO.class);
        return dto;
    }
    @Override
    public void updatePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        positionSalaryDao.savePositionSalaryStep(pss);
    }
    @Override
    public void deletePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        positionSalaryDao.deletePosition(pss);
    }
    @Override
    public PositionSalaryStepResponseDTO findPositionSalaryStep(Long positionId) {
        return modelMapper.map(positionDao.findById(positionId), PositionSalaryStepResponseDTO.class);
    }
    /// AllowanceResponse 서비스
    @Override
    public AllowanceResponseDTO insertAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = allowanceDao.saveAllowance(responseDTO.toEntity());
        return modelMapper.map(allowance, AllowanceResponseDTO.class);
    }
    @Override
    public void updateAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = modelMapper.map(responseDTO, AllowanceEntity.class);
        allowanceDao.saveAllowance(allowance);
    }
    @Override
    public void deleteAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = modelMapper.map(responseDTO, AllowanceEntity.class);
        allowanceDao.deleteAllowanceById(allowance.getAllowanceId());
    }
    @Override
    public AllowanceResponseDTO findAllowance(Long allowanceId) {
        return modelMapper.map(allowanceDao.findAllowanceById(allowanceId), AllowanceResponseDTO.class);
    }
    /// Deduction 서비스
    @Override
    public DeductionResponseDTO insertDeduction(DeductionResponseDTO responseDTO) {
        DeductionEntity dd = deductionDao.save(responseDTO.toEntity());
        return modelMapper.map(dd, DeductionResponseDTO.class);
    }
    @Override
    public void updateDeduction(DeductionResponseDTO responseDTO) {
        DeductionEntity deduction = modelMapper.map(responseDTO, DeductionEntity.class);
        deductionDao.save(deduction);
    }
    @Override
    public void deleteDeduction(DeductionResponseDTO responseDTO) {
        deductionDao.deleteById(responseDTO.getDeductionId());
    }
    @Override
    public DeductionResponseDTO findDeduction(Long deductionId) {
        return modelMapper.map(deductionDao.fetchById(deductionId), DeductionResponseDTO.class);
    }
    /// PayStub 서비스
    @Override
    public PayStubResponseDTO insertPayStub(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        PayStubEntity ps = payStubDao.save(pp);
        return modelMapper.map(ps, PayStubResponseDTO.class);
    }
    @Override
    public void updateDeduction(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        payStubDao.save(pp);
    }
    @Override
    public void deleteDeduction(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        payStubDao.delete(responseDTO.toEntity());
    }
    @Override
    public PayStubResponseDTO findPayStub(Long payStubId) {
        return modelMapper.map(deductionDao.fetchById(payStubId), PayStubResponseDTO.class);
    }
    /// Salary 서비스
    @Override
    public SalaryResponseDTO insertSalary(SalaryResponseDTO responseDTO) {
        SalaryEntity salary = salaryDao.saveSalary(responseDTO.toEntity());
        return modelMapper.map(salary, SalaryResponseDTO.class);
    }
    @Override
    public void updateSalary(SalaryResponseDTO responseDTO) {
        SalaryEntity salary = salaryDao.saveSalary(responseDTO.toEntity());
        salaryDao.saveSalary(salary);
    }
    @Override
    public void deleteSalary(SalaryResponseDTO responseDTO) {
        salaryDao.deleteSalary(responseDTO.toEntity());
    }
    @Override
    public SalaryResponseDTO findSalary(Long salaryId) {
       return modelMapper.map(salaryDao.findSalaryById(salaryId), SalaryResponseDTO.class);
    }
}
