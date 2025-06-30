package com.unity.tribe.domain.member.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.domain.group.repository.GroupRepository;
import com.unity.tribe.domain.group.repository.MemberRepository;
import com.unity.tribe.domain.member.dto.response.MemberResponseDto;
import com.unity.tribe.domain.member.entity.MemberEntity;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<MemberResponseDto> getMembers(String groupId, String username) {
        // 그룹 존재 확인
        if (!groupRepository.existsById(groupId)) {
            throw new TribeApiException(ErrorCode.GROUP_NOT_FOUND);
        }

        // 그룹의 모든 멤버 조회
        List<MemberEntity> members = memberRepository.findAllByGroupId(groupId);

        return members.stream()
                .filter(member -> member.getDeletedAt() == null) // 삭제되지 않은 멤버만
                .map(member -> {
                    Optional<UserEntity> user = userRepository.findById(member.getUserId());
                    return MemberResponseDto.builder()
                            .userId(member.getUserId())
                            .nickname(user.map(UserEntity::getNickname).orElse("탈퇴한 사용자"))
                            .profileImg(user.map(UserEntity::getProfileImg).orElse(null))
                            .role(member.getRole())
                            .status(member.getStatus() != null ? member.getStatus() : "ACTIVE")
                            .joinedAt(member.getCreatedAt() != null ? member.getCreatedAt().toLocalDateTime()
                                    : LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void kickMember(String groupId, String memberId, String hostId) {
        // 그룹 존재 확인
        if (!groupRepository.existsById(groupId)) {
            throw new TribeApiException(ErrorCode.GROUP_NOT_FOUND);
        }

        // 호스트 권한 확인
        Optional<MemberEntity> hostMember = memberRepository.findByGroupIdAndUserId(groupId, hostId);
        if (hostMember.isEmpty() || !"HOST".equals(hostMember.get().getRole())) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 강제 탈퇴시킬 멤버 조회
        Optional<MemberEntity> targetMember = memberRepository.findByGroupIdAndUserId(groupId, memberId);
        if (targetMember.isEmpty()) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 자기 자신을 강제 탈퇴시킬 수 없음
        if (hostId.equals(memberId)) {
            throw new TribeApiException(ErrorCode.MEMBER_CANNOT_KICK_SELF);
        }

        // 호스트를 강제 탈퇴시킬 수 없음
        if ("HOST".equals(targetMember.get().getRole())) {
            throw new TribeApiException(ErrorCode.MEMBER_CANNOT_KICK_HOST);
        }

        // 소프트 삭제 처리
        MemberEntity member = targetMember.get();
        member.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        member.setStatus("EXITED");
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void changeMemberRole(String groupId, String memberId, String role, String hostId) {
        // 그룹 존재 확인
        if (!groupRepository.existsById(groupId)) {
            throw new TribeApiException(ErrorCode.GROUP_NOT_FOUND);
        }

        // 호스트 권한 확인
        Optional<MemberEntity> hostMember = memberRepository.findByGroupIdAndUserId(groupId, hostId);
        if (hostMember.isEmpty() || !"HOST".equals(hostMember.get().getRole())) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 역할 변경할 멤버 조회
        Optional<MemberEntity> targetMember = memberRepository.findByGroupIdAndUserId(groupId, memberId);
        if (targetMember.isEmpty()) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 유효한 역할인지 확인
        if (!"MEMBER".equals(role) && !"HOST".equals(role)) {
            throw new TribeApiException(ErrorCode.INVALID_MEMBER_ROLE);
        }

        // 역할 변경
        MemberEntity member = targetMember.get();
        member.setRole(role);
        memberRepository.save(member);
    }
}