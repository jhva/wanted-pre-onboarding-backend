package com.api.wantedpreonboardingbackend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import com.api.wantedpreonboardingbackend.common.BaseTimeEntity;
import com.api.wantedpreonboardingbackend.service.dto.ApplyHistoryDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apply_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplyHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "apply_id", updatable = false, nullable = false)
    private UUID applyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job")
    private JobPost jobPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public ApplyHistory(JobPost jobPost, User user) {
        this.jobPost = jobPost;
        this.user = user;
    }

}
