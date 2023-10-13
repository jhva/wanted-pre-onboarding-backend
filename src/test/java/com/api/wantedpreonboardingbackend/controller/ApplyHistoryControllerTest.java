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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.wantedpreonboardingbackend.entity.JobPost;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.service.dto.ApplyHistoryDto;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplyHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ApplyHistoryDto.SaveRequest saveRequestDto;
    private JobPost jobPost;
    private User user;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String API_V1_POST = "/api/v1";

    @BeforeEach
    void setUp() {

        jobPost = JobPost.builder().jobId(UUID.randomUUID()).build();
        user = User.builder().id(UUID.randomUUID()).build();

        saveRequestDto = ApplyHistoryDto.SaveRequest.builder()
            .jobId(jobPost.getJobId())
            .userId(user.getUserId())
            .build();

    }

    @Test
    @DisplayName("채용 공고 지원 컨트롤러 테스트")
    void createCompany() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_V1_POST + "/apply")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveRequestDto)));

        resultActions.andDo(print());

    }
}
