package com.api.wantedpreonboardingbackend.entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.api.wantedpreonboardingbackend.common.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name="job")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JobPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "job_id", columnDefinition = "BINARY(16)")
    private UUID jobId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company")
    private Company company;
    @Column(name = "job_position")
    private String jobPosition;

    @Column(name = "job_compensation")
    private int jobCompensation;

    @Column(name = "job_tech")
    private String jobTech;

    @Column(name = "job_description")
    private String jobDescription;

    @Builder
    public JobPost (final UUID jobId,final Company company,final String jobPosition,final int jobCompensation,final String jobTech,final String jobDescription){
        this.jobId = jobId;
        this.company = company;
        this.jobPosition = jobPosition;
        this.jobCompensation = jobCompensation;
        this.jobTech = jobTech;
        this.jobDescription = jobDescription;
    }


}
