package com.playdata.HumanResourceManagement.company.repository;

import com.playdata.HumanResourceManagement.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByCompanyCode(String companyCode);
}
