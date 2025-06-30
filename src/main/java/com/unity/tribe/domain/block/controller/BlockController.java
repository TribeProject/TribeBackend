package com.unity.tribe.domain.block.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.block.docs.BlockApi;
import com.unity.tribe.domain.block.docs.CreateBlock;
import com.unity.tribe.domain.block.docs.DeleteBlock;
import com.unity.tribe.domain.block.docs.GetBlocks;
import com.unity.tribe.domain.block.dto.request.BlockCreateRequestDto;
import com.unity.tribe.domain.block.dto.response.BlockResponseDto;
import com.unity.tribe.domain.block.service.BlockService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 차단 관련 컨트롤러
 */
@RestController
@RequestMapping("/v1/blocks")
@RequiredArgsConstructor
@BlockApi
public class BlockController {

    private final BlockService blockService;

    /**
     * 사용자를 차단합니다.
     * 
     * @param request     차단 요청 데이터
     * @param userDetails 인증된 사용자 정보
     * @return 차단 정보
     */
    @PostMapping
    @CreateBlock
    public ResponseEntity<ApiResponseDto<BlockResponseDto>> blockUser(
            @Valid @RequestBody BlockCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        BlockResponseDto block = blockService.blockUser(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("사용자가 성공적으로 차단되었습니다.", block));
    }

    /**
     * 사용자 차단을 해제합니다.
     * 
     * @param blockedUserId 차단 해제할 사용자 ID
     * @param userDetails   인증된 사용자 정보
     * @return 해제 성공 메시지
     */
    @DeleteMapping("/{blockedUserId}")
    @DeleteBlock
    public ResponseEntity<ApiResponseDto<Void>> unblockUser(
            @Parameter(description = "차단 해제할 사용자 ID") @PathVariable String blockedUserId,
            @AuthenticationPrincipal UserDetails userDetails) {

        blockService.unblockUser(blockedUserId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("차단이 성공적으로 해제되었습니다.", null));
    }

    /**
     * 사용자의 차단 목록을 조회합니다.
     * 
     * @param page        페이지 번호 (0부터 시작)
     * @param size        페이지 크기
     * @param userDetails 인증된 사용자 정보
     * @return 페이징된 차단 목록
     */
    @GetMapping
    @GetBlocks
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<BlockResponseDto>>>> getBlocks(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        CommonPageDto<List<BlockResponseDto>> blocks = blockService.getBlockList(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(ApiResponseDto.success(blocks));
    }
}