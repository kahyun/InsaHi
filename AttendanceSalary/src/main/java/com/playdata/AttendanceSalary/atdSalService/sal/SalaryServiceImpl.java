package com.playdata.attendanceSalary.atdSalService.sal;


import com.playdata.attendanceSalary.atdSalDao.sal.SalaryDao;
import com.playdata.attendanceSalary.atdSalDto.sal.PositionResponseDTO;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.attendanceSalary.atdSalRepository.sal.PositionRepository;
import com.playdata.attendanceSalary.atdSalRepository.sal.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Position;

@Service

@RequiredArgsConstructor
@Slf4j
public class SalaryServiceImpl  implements SalaryService {

    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    // 회사에서 직급 추가
    @Override
    public PositionResponseDTO insertPosition(PositionEntity positionEntity) {
        Position position = modelMapper.map(positionEntity, Position.class);
        positionEntity.setCompanyCode(CompanyCode);
        position.
        return positionRepository.save(positionEntity);
    }
    // 회사에서 직급 수정
    @Override
    public void updatePosition(PositionEntity positionEntity, String CompanyCode) {
        positionEntity.setCompanyCode(CompanyCode);
        positionRepository.save(positionEntity);
    }
    // 회사에서 직급 삭제
    @Override
    public void deletePosition(PositionEntity positionEntity, String CompanyCode) {
        positionRepository.delete(positionEntity);
    }
}
