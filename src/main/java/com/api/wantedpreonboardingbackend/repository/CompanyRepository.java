package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.api.wantedpreonboardingbackend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CustomJobPostRepository {
}
