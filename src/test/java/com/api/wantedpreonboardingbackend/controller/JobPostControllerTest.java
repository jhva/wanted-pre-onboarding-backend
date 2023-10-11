package com.api.wantedpreonboardingbackend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.JobService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.JobPostDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class JobPostControllerTest {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    private JobPostDto.SaveRequest saveRequestDto;

    private JobPostDto.SaveResponseJobDto jobPost;
    private Company company;

    @Autowired
    private ObjectMapper objectMapper;

    private static final UUID id = UUID.fromString("75e486bc-6bd1-46b5-b738-19fb288911ac");
    private static final UUID jobId = UUID.fromString("0fc291ab-a591-47e4-b7db-0c8f27d1f411");

    private static final String API_V1_POST = "/api/v1/job";

    @BeforeEach
    void setUp() {
        Company newCompany = getCompany(id);
        saveRequestDto = JobPostDto.SaveRequest.builder()
            .jobTech("Go")
            .jobCompensation(1000000)
            .jobPosition("풀스택 주니어 개발자")
            .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
            .company(newCompany.getCompanyId())
            .build();
    }

    @Test
    @DisplayName("채용 공고 등록 컨트롤러 테스트")
    void createJobPostController() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_V1_POST + "/create-job")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveRequestDto)));
        resultActions.andDo(print());

    }

    @Test
    @DisplayName("채용 공고 수정 컨트롤러 테스트")
    void updateJobPostController() throws Exception {
        Company newCompany = getCompany(id);
        saveRequestDto = JobPostDto.SaveRequest.builder()
            .jobTech("Go")
            .jobCompensation(1000000)
            .jobPosition("백엔드 주니어 개발자")
            .jobDescription("원티드랩에서 백엔드 주니어 개발자를 채용합니다.")
            .company(newCompany.getCompanyId())
            .build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(API_V1_POST + "/update-job/{jobId}", jobId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveRequestDto)));
        resultActions.andDo(print());

    }

    @Test
    @DisplayName("채용 공고 삭제 컨트롤러 테스트")
    void deleteJobPostController() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(API_V1_POST + "/delete-job/{jobId}", jobId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        resultActions.andDo(print());

    }

    @Test
    @DisplayName("채용 공고 전체 조회 컨트롤러 테스트")
    void allJobPostController() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_V1_POST + "/all-job")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        resultActions.andDo(print());

    }

    @Test
    @DisplayName("채용 공고 상세 조회 컨트롤러 테스트")
    void searchJobPostController() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_V1_POST + "/detail-job/{jobId}", jobId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        resultActions.andDo(print());

    }

    @Transactional
    private Company getCompany(UUID id) {

        Company company = companyRepository.findById(id).orElseThrow();
        return company;
    }
}
