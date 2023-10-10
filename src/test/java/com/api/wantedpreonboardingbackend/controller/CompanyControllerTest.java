package com.api.wantedpreonboardingbackend.controller;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;

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
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    private CompanyDto.SaveRequest saveRequestDto;

    private Company company;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_V1_POST = "/api/v1/company";

    @BeforeEach
    void setUp() {
        saveRequestDto = CompanyDto.SaveRequest.builder()
            .companyArea("판교")
            .companyName("원티드랩")
            .companyCountry("한국").build();
    }

    @Test
    @DisplayName("회사 등록 컨트롤러 테스트")
    void createCompany() throws Exception {
        //given
        company = companyService.createCompany(saveRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_V1_POST + "/create-company")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(company)));

        //then
        resultActions.andExpect(status().isOk()).andDo(print());
    }
}
