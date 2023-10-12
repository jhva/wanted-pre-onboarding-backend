package com.api.wantedpreonboardingbackend.service;

import org.springframework.stereotype.Service;

import com.api.wantedpreonboardingbackend.entity.Company;
import com.api.wantedpreonboardingbackend.entity.User;
import com.api.wantedpreonboardingbackend.repository.CompanyRepository;
import com.api.wantedpreonboardingbackend.repository.UserRepository;
import com.api.wantedpreonboardingbackend.service.dto.CompanyDto;
import com.api.wantedpreonboardingbackend.service.dto.UserDto;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDto.SaveRequest saveRequest) {
        User user = UserDto.toEntity(saveRequest);
        return userRepository.save(user);

    }
}
