package com.playdata.HumanResourceManagement.company.dao;

import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


public interface CompanyDAO {
    public void insert(Company company);
}
