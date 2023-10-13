package com.api.wantedpreonboardingbackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.wantedpreonboardingbackend.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
