package com.unity.tribe.domain.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.unity.tribe.domain.group.dto.request.GroupFilterRequestDto;
import com.unity.tribe.domain.group.entity.GroupEntity;

/**
 * 그룹 동적 검색을 위한 Custom Repository 인터페이스
 */
public interface GroupRepositoryCustom {

    /**
     * 필터 조건에 따른 동적 그룹 검색
     * 
     * @param filter   검색 필터 조건
     * @param pageable 페이징 정보
     * @return 검색된 그룹 페이지
     */
    Page<GroupEntity> findGroupsWithFilter(GroupFilterRequestDto filter, Pageable pageable);

    /**
     * 키워드로 그룹 검색 (제목, 설명에서 검색)
     * 
     * @param keyword  검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 그룹 페이지
     */
    Page<GroupEntity> findGroupsByKeyword(String keyword, Pageable pageable);

    /**
     * 카테고리와 상태로 그룹 검색
     * 
     * @param categoryCode 카테고리 코드
     * @param status       그룹 상태
     * @param pageable     페이징 정보
     * @return 검색된 그룹 페이지
     */
    Page<GroupEntity> findGroupsByCategoryAndStatus(String categoryCode, String status, Pageable pageable);

    /**
     * 지역으로 그룹 검색
     * 
     * @param regions  지역 목록
     * @param pageable 페이징 정보
     * @return 검색된 그룹 페이지
     */
    Page<GroupEntity> findGroupsByRegions(java.util.List<String> regions, Pageable pageable);
}