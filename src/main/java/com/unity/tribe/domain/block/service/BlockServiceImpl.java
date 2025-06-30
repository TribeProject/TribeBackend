package com.unity.tribe.domain.block.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.block.dto.request.BlockCreateRequestDto;
import com.unity.tribe.domain.block.dto.response.BlockResponseDto;
import com.unity.tribe.domain.block.entity.BlockEntity;
import com.unity.tribe.domain.block.repository.BlockRepository;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    @Override
    public BlockResponseDto blockUser(BlockCreateRequestDto request, String userId) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 차단 대상 사용자 검증
        Optional<UserEntity> blockedUser = userRepository.findById(request.getBlockedUserId());
        if (blockedUser.isEmpty()) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 자기 자신을 차단할 수 없음
        if (userId.equals(request.getBlockedUserId())) {
            throw new TribeApiException(ErrorCode.CANNOT_BLOCK_SELF);
        }

        // 이미 차단된 사용자인지 확인
        if (blockRepository.existsByUserIdAndBlockedUserId(userId, request.getBlockedUserId())) {
            throw new TribeApiException(ErrorCode.ALREADY_BLOCKED);
        }

        // 차단 엔티티 생성
        BlockEntity blockEntity = BlockEntity.builder()
                .userId(userId)
                .blockedUserId(request.getBlockedUserId())
                .createdAt(LocalDateTime.now())
                .status(BlockEntity.BlockStatus.ACTIVE)
                .build();

        BlockEntity savedBlock = blockRepository.save(blockEntity);

        return BlockResponseDto.builder()
                .blockId(savedBlock.getBlockId())
                .userId(savedBlock.getUserId())
                .blockedUser(BlockResponseDto.BlockedUserDto.builder()
                        .userId(blockedUser.get().getUserId())
                        .nickname(blockedUser.get().getNickname())
                        .profileImage(blockedUser.get().getProfileImg())
                        .build())
                .createdAt(savedBlock.getCreatedAt())
                .build();
    }

    @Override
    public void unblockUser(String blockedUserId, String userId) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 차단 엔티티 조회
        Optional<BlockEntity> blockEntity = blockRepository.findByUserIdAndBlockedUserId(userId, blockedUserId);
        if (blockEntity.isEmpty()) {
            throw new TribeApiException(ErrorCode.NOT_BLOCKED_USER);
        }

        blockRepository.delete(blockEntity.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<BlockResponseDto>> getBlockList(String userId, Pageable pageable) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        Page<BlockEntity> blockPage = blockRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        List<BlockResponseDto> blockList = blockPage.getContent().stream()
                .map(block -> {
                    Optional<UserEntity> blockedUser = userRepository.findById(block.getBlockedUserId());
                    return BlockResponseDto.builder()
                            .blockId(block.getBlockId())
                            .userId(block.getUserId())
                            .blockedUser(BlockResponseDto.BlockedUserDto.builder()
                                    .userId(block.getBlockedUserId())
                                    .nickname(blockedUser.map(UserEntity::getNickname).orElse("탈퇴한 사용자"))
                                    .profileImage(blockedUser.map(UserEntity::getProfileImg).orElse(null))
                                    .build())
                            .createdAt(block.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        return CommonPageDto.of(
                blockPage.getNumber(),
                blockPage.getSize(),
                (int) blockPage.getTotalElements(),
                blockPage.getTotalPages(),
                blockList);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBlocked(String userId, String blockedUserId) {
        return blockRepository.existsByUserIdAndBlockedUserId(userId, blockedUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getBlockedUserIds(String userId) {
        return blockRepository.findBlockedUserIdsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMutuallyBlocked(String userId1, String userId2) {
        return blockRepository.existsMutualBlock(userId1, userId2);
    }
}