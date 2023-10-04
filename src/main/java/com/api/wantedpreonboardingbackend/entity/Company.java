package com.api.wantedpreonboardingbackend.entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.api.wantedpreonboardingbackend.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "company_id", columnDefinition = "BINARY(16)")
    private UUID companyId;

    @Column(name = "company_country")
    private String companyCountry;

    @Column(name = "company_area")
    private String companyArea;


    @Builder
    public Company (final UUID companyId,final String companyCountry,final String companyArea){
        this.companyId = companyId;
        this.companyCountry = companyCountry;
        this.companyArea = companyArea;
    }

    public static Company createCompany(final UUID companyId,final String companyCountry,final String companyArea) {
        return Company.builder()
            .companyId(companyId)
            .companyArea(companyArea)
            .companyCountry(companyCountry).build();
    }


}
