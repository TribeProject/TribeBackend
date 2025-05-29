package com.unity.tribe.domain.block.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.block.dto.request.BlockCreateRequestDto;
import com.unity.tribe.domain.block.dto.response.BlockResponseDto;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {

    @Override
    public BlockResponseDto blockUser(BlockCreateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public void unblockUser(String blockedUserId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public CommonPageDto<List<BlockResponseDto>> getBlockList(String userId, Pageable pageable) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public boolean isBlocked(String userId, String blockedUserId) {
        // TODO: 구현 필요
        return false;
    }

    @Override
    public List<String> getBlockedUserIds(String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public boolean isMutuallyBlocked(String userId1, String userId2) {
        // TODO: 구현 필요
        return false;
    }
}