package com.unity.tribe.domain.block.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.block.dto.request.BlockCreateRequestDto;
import com.unity.tribe.domain.block.dto.response.BlockResponseDto;

public interface BlockService {

    /**
     * 사용자를 차단합니다.
     * 
     * @param request 차단 요청 정보
     * @param userId  사용자 ID
     * @return 차단 정보
     */
    BlockResponseDto blockUser(BlockCreateRequestDto request, String userId);

    /**
     * 사용자 차단을 해제합니다.
     * 
     * @param blockedUserId 차단 해제할 사용자 ID
     * @param userId        사용자 ID
     */
    void unblockUser(String blockedUserId, String userId);

    /**
     * 사용자의 차단 목록을 조회합니다.
     * 
     * @param userId   사용자 ID
     * @param pageable 페이지 정보
     * @return 차단 목록
     */
    CommonPageDto<List<BlockResponseDto>> getBlockList(String userId, Pageable pageable);

    /**
     * 차단 여부를 확인합니다.
     * 
     * @param userId        사용자 ID
     * @param blockedUserId 확인할 사용자 ID
     * @return 차단 여부
     */
    boolean isBlocked(String userId, String blockedUserId);

    /**
     * 사용자가 차단한 사용자 ID 목록을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 차단된 사용자 ID 목록
     */
    List<String> getBlockedUserIds(String userId);

    /**
     * 상호 차단 관계인지 확인합니다.
     * 
     * @param userId1 사용자1 ID
     * @param userId2 사용자2 ID
     * @return 상호 차단 여부
     */
    boolean isMutuallyBlocked(String userId1, String userId2);
}