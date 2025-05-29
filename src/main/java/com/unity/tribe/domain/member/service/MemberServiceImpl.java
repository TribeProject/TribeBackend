package com.unity.tribe.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.domain.member.dto.response.MemberResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Override
    public List<MemberResponseDto> getMembers(String groupId, String username) {
        // TODO: 그룹 내 전체 멤버 리스트 조회 로직
        return List.of(); // 임시 반환
    }

    @Override
    public void kickMember(String groupId, String memberId, String hostId) {
        // TODO: 멤버 강제 탈퇴 로직
    }

    @Override
    public void changeMemberRole(String groupId, String memberId, String role, String hostId) {
        // TODO: 멤버 역할 변경 로직
    }
}