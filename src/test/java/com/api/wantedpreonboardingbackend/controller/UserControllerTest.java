package com.api.wantedpreonboardingbackend.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.service.CompanyService;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserDto.SaveRequest saveRequestDto;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_V1_POST = "/api/v1/";

    @BeforeEach
    void setUp() {
        saveRequestDto = UserDto.SaveRequest.builder()
            .userName("김정훈님").build();
    }

    @Test
    @DisplayName("유저 등록 컨트롤러 테스트")
    void createUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_V1_POST + "/create-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveRequestDto)));
        resultActions.andExpect(status().isOk()).andDo(print());

    }
}
