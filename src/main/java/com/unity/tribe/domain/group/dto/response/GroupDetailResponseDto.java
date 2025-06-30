package com.unity.tribe.domain.group.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unity.tribe.domain.member.dto.response.MemberResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDetailResponseDto {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String categoryCode;
    private String categoryName;
    private Integer currentMembers;
    private Integer maxMembers;
    private String status;
    private String leaderId;
    private String leaderName;
    private List<MemberResponseDto> members;
    private List<GoalResponseDto> goals;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}