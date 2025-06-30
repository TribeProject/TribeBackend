package com.unity.tribe.domain.group.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.docs.*;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupFilterRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;
import com.unity.tribe.domain.group.service.GroupService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 모임 관련 컨트롤러
 */
@RestController
@RequestMapping("/v1/groups")
@RequiredArgsConstructor
@GroupApi
public class GroupController {

    private final GroupService groupService;

    /**
     * 모임 목록을 조회합니다.
     * 
     * @param page              페이지 번호 (0부터 시작)
     * @param size              페이지 크기
     * @param sort              정렬 조건 (createdAt,desc | participants,desc |
     *                          title,asc)
     * @param keyword           검색 키워드 (모임명, 모임 설명글에서 검색)
     * @param status            모임 상태 필터
     * @param startDate         모임 시작일 필터
     * @param endDate           모임 종료일 필터
     * @param regions           지역 필터 (쉼표로 구분)
     * @param minAge            최소 나이 제한
     * @param maxAge            최대 나이 제한
     * @param genderRestriction 성별 제한 (NONE, MALE, FEMALE)
     * @param minParticipants   최소 참여 인원
     * @param maxParticipants   최대 참여 인원
     * @param categories        모임 주제 (쉼표로 구분)
     * @return 페이징된 모임 목록
     */
    @GetMapping
    @GetGroups
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> getGroups(
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")) @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")) @RequestParam(defaultValue = "20") int size,

            @Parameter(name = "sort", description = "정렬 조건", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "createdAt,desc", "createdAt,asc", "participants,desc", "participants,asc", "title,asc",
                    "title,desc" }, defaultValue = "createdAt,desc")) @RequestParam(defaultValue = "createdAt,desc") String sort,

            @Parameter(name = "keyword", description = "검색 키워드(모임명, 모임 설명글)", in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "러닝")) @RequestParam(required = false) String keyword,

            @Parameter(name = "status", description = "모임 상태", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "WAITING", "ONGOING", "FINISHED", "DISBANDED" })) @RequestParam(required = false) String status,

            @Parameter(name = "startDate", description = "모임 시작일(YYYY-MM-DD)", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date", example = "2025-05-24")) @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,

            @Parameter(name = "endDate", description = "모임 종료일(YYYY-MM-DD)", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date", example = "2025-12-31")) @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,

            @Parameter(name = "regions", description = "지역 필터 (쉼표로 구분, e.g. 서울,경기,인천)", in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "서울,경기,인천")) @RequestParam(required = false) List<String> regions,

            @Parameter(name = "minAge", description = "최소 나이 제한", in = ParameterIn.QUERY, schema = @Schema(type = "integer", minimum = "0", maximum = "100", example = "25")) @RequestParam(required = false) Integer minAge,

            @Parameter(name = "maxAge", description = "최대 나이 제한", in = ParameterIn.QUERY, schema = @Schema(type = "integer", minimum = "0", maximum = "100", example = "30")) @RequestParam(required = false) Integer maxAge,

            @Parameter(name = "genderRestriction", description = "성별 제한", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "NONE", "MALE",
                    "FEMALE" }, defaultValue = "NONE")) @RequestParam(required = false) String genderRestriction,

            @Parameter(name = "minParticipants", description = "최소 참여 인원", in = ParameterIn.QUERY, schema = @Schema(type = "integer", minimum = "1", example = "10")) @RequestParam(required = false) Integer minParticipants,

            @Parameter(name = "maxParticipants", description = "최대 참여 인원", in = ParameterIn.QUERY, schema = @Schema(type = "integer", minimum = "1", example = "100")) @RequestParam(required = false) Integer maxParticipants,

            @Parameter(name = "categories", description = "모임 주제(쉼표로 구분)", in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "F,T,EA")) @RequestParam(required = false) List<String> categories) {

        // 페이징 및 정렬 설정
        Pageable pageable = createPageable(page, size, sort);

        // 필터 조건 설정
        GroupFilterRequestDto filterRequest = GroupFilterRequestDto.builder()
                .keyword(keyword)
                .status(status)
                .startDate(startDate)
                .endDate(endDate)
                .regions(regions)
                .minAge(minAge)
                .maxAge(maxAge)
                .genderRestriction(genderRestriction)
                .minParticipants(minParticipants)
                .maxParticipants(maxParticipants)
                .categories(categories)
                .build();

        // 서비스 계층에 필터링된 모임 목록 요청
        CommonPageDto<List<GroupListResponseDto>> groups = groupService.getGroupsWithFilter(pageable, filterRequest);
        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    /**
     * 특정 카테고리의 모임 목록을 조회합니다.
     * 
     * @param categoryCode      조회할 카테고리 코드 (e.g. EA, F, T 등)
     * @param page              페이지 번호
     * @param size              페이지 크기
     * @param sort              정렬 조건
     * @param keyword           검색 키워드
     * @param status            모임 상태
     * @param startDate         시작일
     * @param endDate           종료일
     * @param regions           지역 목록
     * @param minAge            최소 나이
     * @param maxAge            최대 나이
     * @param genderRestriction 성별 제한
     * @param minParticipants   최소 참여자 수
     * @param maxParticipants   최대 참여자 수
     * @return 필터링된 모임 목록
     */
    @GetMapping("/category/{categoryCode}")
    @GetGroupsByCategory
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> getGroupsByCategory(
            @Parameter(name = "categoryCode", description = "카테고리 코드", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "EA")) @PathVariable String categoryCode,

            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")) @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")) @RequestParam(defaultValue = "20") int size,

            @Parameter(name = "sort", description = "정렬 조건", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "createdAt,desc", "createdAt,asc", "participants,desc", "participants,asc", "title,asc",
                    "title,desc" }, defaultValue = "createdAt,desc")) @RequestParam(defaultValue = "createdAt,desc") String sort,

            @Parameter(name = "keyword", description = "검색 키워드", in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "러닝")) @RequestParam(required = false) String keyword,

            @Parameter(name = "status", description = "모임 상태", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "WAITING", "ONGOING", "FINISHED", "DISBANDED" })) @RequestParam(required = false) String status,

            @Parameter(name = "startDate", description = "모임 시작일 필터", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date")) @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,

            @Parameter(name = "endDate", description = "모임 종료일 필터", in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date")) @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,

            @Parameter(name = "regions", description = "지역 필터", in = ParameterIn.QUERY, schema = @Schema(type = "string")) @RequestParam(required = false) List<String> regions,

            @Parameter(name = "minAge", description = "최소 나이 제한", in = ParameterIn.QUERY, schema = @Schema(type = "integer")) @RequestParam(required = false) Integer minAge,

            @Parameter(name = "maxAge", description = "최대 나이 제한", in = ParameterIn.QUERY, schema = @Schema(type = "integer")) @RequestParam(required = false) Integer maxAge,

            @Parameter(name = "genderRestriction", description = "성별 제한", in = ParameterIn.QUERY, schema = @Schema(type = "string", allowableValues = {
                    "NONE", "MALE", "FEMALE" })) @RequestParam(required = false) String genderRestriction,

            @Parameter(name = "minParticipants", description = "최소 참여 인원", in = ParameterIn.QUERY, schema = @Schema(type = "integer")) @RequestParam(required = false) Integer minParticipants,

            @Parameter(name = "maxParticipants", description = "최대 참여 인원", in = ParameterIn.QUERY, schema = @Schema(type = "integer")) @RequestParam(required = false) Integer maxParticipants) {

        // 페이징 및 정렬 설정
        Pageable pageable = createPageable(page, size, sort);

        // 카테고리 코드를 직접 서비스에 전달
        CommonPageDto<List<GroupListResponseDto>> groups = groupService.getGroupsByCategory(
                categoryCode, pageable, status);

        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    /**
     * 키워드로 모임을 검색합니다.
     * 
     * @param keyword 검색할 키워드
     * @param page    페이지 번호
     * @param size    페이지 크기
     * @param sort    정렬 조건
     * @return 검색된 모임 목록
     */
    @GetMapping("/search")
    @SearchGroups
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> searchGroups(
            @Parameter(name = "keyword", description = "검색 키워드", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "러닝")) @RequestParam String keyword,

            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")) @RequestParam(defaultValue = "0") int page,

            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20")) @RequestParam(defaultValue = "20") int size,

            @Parameter(name = "sort", description = "정렬 조건", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "createdAt,desc")) @RequestParam(defaultValue = "createdAt,desc") String sort) {

        // 페이징 및 정렬 설정
        Pageable pageable = createPageable(page, size, sort);

        // 키워드 기반 필터 생성
        GroupFilterRequestDto filterRequest = GroupFilterRequestDto.builder()
                .keyword(keyword)
                .build();

        // 서비스 계층에 검색 요청
        CommonPageDto<List<GroupListResponseDto>> groups = groupService.getGroupsWithFilter(pageable, filterRequest);
        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    /**
     * 정렬 조건에 따른 Pageable 객체 생성
     * 
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sort 정렬 조건 문자열
     * @return 설정된 Pageable 객체
     */
    private Pageable createPageable(int page, int size, String sort) {
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
                return PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
            }
        }
        // 기본 정렬: 생성일 기준 내림차순
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    /**
     * 특정 모임의 상세 정보를 조회합니다.
     * 
     * @param groupId 조회할 모임의 ULID
     * @return 모임 상세 정보
     */
    @GetMapping("/{groupId}")
    @GetGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> getGroup(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId) {

        GroupDetailResponseDto group = groupService.getGroup(groupId);
        return ResponseEntity.ok(ApiResponseDto.success(group));
    }

    /**
     * 새로운 모임을 생성합니다.
     * 
     * @param request     모임 생성 요청 데이터
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 모임 정보
     */
    @PostMapping
    @CreateGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> createGroup(
            @Valid @RequestBody GroupCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GroupDetailResponseDto group = groupService.createGroup(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("모임이 성공적으로 생성되었습니다.", group));
    }

    /**
     * 기존 모임 정보를 수정합니다.
     * 
     * @param groupId     수정할 모임 ULID
     * @param request     수정할 모임 정보
     * @param userDetails 인증된 사용자 정보
     * @return 수정된 모임 정보
     */
    @PutMapping("/{groupId}")
    @UpdateGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> updateGroup(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @Valid @RequestBody GroupUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GroupDetailResponseDto group = groupService.updateGroup(groupId, request,
                userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("모임이 성공적으로 수정되었습니다.", group));
    }

    /**
     * 모임을 삭제합니다.
     * 
     * @param groupId     삭제할 모임 ULID
     * @param userDetails 인증된 사용자 정보
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{groupId}")
    @DeleteGroup
    public ResponseEntity<ApiResponseDto<Void>> deleteGroup(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.deleteGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("모임이 성공적으로 삭제되었습니다.", null));
    }

    /**
     * 모임에 참여합니다.
     * 
     * @param groupId     참여할 모임 ULID
     * @param userDetails 인증된 사용자 정보
     * @return 참여 성공 메시지
     */
    @PostMapping("/{groupId}/join")
    @JoinGroup
    public ResponseEntity<ApiResponseDto<Void>> joinGroup(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.joinGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("모임에 성공적으로 참여했습니다.", null));
    }

    /**
     * 모임에서 탈퇴합니다.
     * 
     * @param groupId     탈퇴할 모임 ULID
     * @param userDetails 인증된 사용자 정보
     * @return 탈퇴 성공 메시지
     */
    @DeleteMapping("/{groupId}/leave")
    @LeaveGroup
    public ResponseEntity<ApiResponseDto<Void>> leaveGroup(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.leaveGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("모임에서 성공적으로 탈퇴했습니다.", null));
    }

}
