package com.api.wantedpreonboardingbackend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.api.wantedpreonboardingbackend.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "company_id", updatable = false, nullable = false)
    private UUID companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_country")
    private String companyCountry;

    @Column(name = "company_area")
    private String companyArea;

    @OneToMany(mappedBy = "companyId")
    private List<JobPost> jobPosts = new ArrayList<>();

    @Builder
    public Company(final UUID companyId, final String companyCountry, final String companyArea, final String companyName) {
        this.companyId = companyId;
        this.companyCountry = companyCountry;
        this.companyArea = companyArea;
        this.companyName = companyName;
    }

    public static Company createCompany(final String companyCountry, final String companyArea, final String companyName) {
        return Company.builder()
            .companyArea(companyArea)
            .companyName(companyName)
            .companyCountry(companyCountry).build();
    }

}
