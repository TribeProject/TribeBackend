package com.unity.tribe.domain.member.service;

import java.util.List;

import com.unity.tribe.domain.member.dto.response.MemberResponseDto;

public interface MemberService {

    List<MemberResponseDto> getMembers(String groupId, String username);

    void kickMember(String groupId, String memberId, String hostId);

    void changeMemberRole(String groupId, String memberId, String role, String hostId);
}