package com.unity.tribe.domain.group.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unity.tribe.domain.group.dto.request.GroupFilterRequestDto;
import com.unity.tribe.domain.group.entity.GroupEntity;
import com.unity.tribe.domain.group.entity.QGroupEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QGroupEntity group = QGroupEntity.groupEntity;

    @Override
    public Page<GroupEntity> findGroupsWithFilter(GroupFilterRequestDto filter, Pageable pageable) {
        log.info("Searching groups with filter: {}", filter);

        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건: 삭제되지 않은 그룹만
        builder.and(group.status.ne(GroupEntity.GroupStatus.DISBANDED));

        // 키워드 검색 (제목, 설명)
        if (hasText(filter.getKeyword())) {
            BooleanExpression keywordCondition = group.title.containsIgnoreCase(filter.getKeyword())
                    .or(group.description.containsIgnoreCase(filter.getKeyword()));
            builder.and(keywordCondition);
        }

        // 상태 필터
        if (hasText(filter.getStatus())) {
            try {
                GroupEntity.GroupStatus status = GroupEntity.GroupStatus.valueOf(filter.getStatus().toUpperCase());
                builder.and(group.status.eq(status));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid group status: {}", filter.getStatus());
            }
        }

        // 카테고리 필터
        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            builder.and(group.categoryCode.in(filter.getCategories()));
        }

        // 날짜 범위 필터
        if (filter.getStartDate() != null) {
            LocalDateTime startDateTime = filter.getStartDate().atStartOfDay();
            builder.and(group.createdAt.goe(java.sql.Timestamp.valueOf(startDateTime)));
        }

        if (filter.getEndDate() != null) {
            LocalDateTime endDateTime = filter.getEndDate().atTime(23, 59, 59);
            builder.and(group.createdAt.loe(java.sql.Timestamp.valueOf(endDateTime)));
        }

        // 지역 필터
        if (filter.getRegions() != null && !filter.getRegions().isEmpty()) {
            BooleanExpression regionCondition = null;
            for (String region : filter.getRegions()) {
                BooleanExpression condition = group.locationAddress.containsIgnoreCase(region);
                regionCondition = regionCondition == null ? condition : regionCondition.or(condition);
            }
            if (regionCondition != null) {
                builder.and(regionCondition);
            }
        }

        // 나이 제한 필터
        if (filter.getMinAge() != null) {
            builder.and(group.minAge.goe(filter.getMinAge()).or(group.minAge.isNull()));
        }

        if (filter.getMaxAge() != null) {
            builder.and(group.maxAge.loe(filter.getMaxAge()).or(group.maxAge.isNull()));
        }

        // 성별 제한 필터
        if (hasText(filter.getGenderRestriction()) && !"NONE".equals(filter.getGenderRestriction())) {
            builder.and(
                    group.genderRestriction.eq(filter.getGenderRestriction()).or(group.genderRestriction.eq("NONE")));
        }

        // 참여 인원 필터
        if (filter.getMinParticipants() != null) {
            builder.and(group.participants.goe(filter.getMinParticipants()));
        }

        if (filter.getMaxParticipants() != null) {
            builder.and(group.participants.loe(filter.getMaxParticipants()));
        }

        // 쿼리 실행
        JPAQuery<GroupEntity> query = queryFactory.selectFrom(group)
                .where(builder);

        // 정렬 적용
        OrderSpecifier<?>[] orderSpecifiers = createOrderSpecifiers(pageable);
        if (orderSpecifiers.length > 0) {
            query.orderBy(orderSpecifiers);
        } else {
            // 기본 정렬: 최신순
            query.orderBy(group.createdAt.desc());
        }

        // 페이징 적용
        List<GroupEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory.selectFrom(group)
                .where(builder)
                .stream().count();

        log.info("Found {} groups with filter conditions", total);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<GroupEntity> findGroupsByKeyword(String keyword, Pageable pageable) {
        log.info("Searching groups by keyword: {}", keyword);

        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건
        builder.and(group.status.ne(GroupEntity.GroupStatus.DISBANDED));

        // 키워드 검색
        if (hasText(keyword)) {
            BooleanExpression keywordCondition = group.title.containsIgnoreCase(keyword)
                    .or(group.description.containsIgnoreCase(keyword));
            builder.and(keywordCondition);
        }

        JPAQuery<GroupEntity> query = queryFactory.selectFrom(group)
                .where(builder)
                .orderBy(group.createdAt.desc());

        List<GroupEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(group)
                .where(builder)
                .stream().count();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<GroupEntity> findGroupsByCategoryAndStatus(String categoryCode, String status, Pageable pageable) {
        log.info("Searching groups by category: {} and status: {}", categoryCode, status);

        BooleanBuilder builder = new BooleanBuilder();

        // 카테고리 조건
        if (hasText(categoryCode)) {
            builder.and(group.categoryCode.eq(categoryCode));
        }

        // 상태 조건
        if (hasText(status)) {
            try {
                GroupEntity.GroupStatus groupStatus = GroupEntity.GroupStatus.valueOf(status.toUpperCase());
                builder.and(group.status.eq(groupStatus));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid group status: {}", status);
                builder.and(group.status.ne(GroupEntity.GroupStatus.DISBANDED));
            }
        } else {
            builder.and(group.status.ne(GroupEntity.GroupStatus.DISBANDED));
        }

        JPAQuery<GroupEntity> query = queryFactory.selectFrom(group)
                .where(builder)
                .orderBy(group.createdAt.desc());

        List<GroupEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(group)
                .where(builder)
                .stream().count();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<GroupEntity> findGroupsByRegions(List<String> regions, Pageable pageable) {
        log.info("Searching groups by regions: {}", regions);

        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건
        builder.and(group.status.ne(GroupEntity.GroupStatus.DISBANDED));

        // 지역 조건
        if (regions != null && !regions.isEmpty()) {
            BooleanExpression regionCondition = null;
            for (String region : regions) {
                BooleanExpression condition = group.locationAddress.containsIgnoreCase(region);
                regionCondition = regionCondition == null ? condition : regionCondition.or(condition);
            }
            if (regionCondition != null) {
                builder.and(regionCondition);
            }
        }

        JPAQuery<GroupEntity> query = queryFactory.selectFrom(group)
                .where(builder)
                .orderBy(group.createdAt.desc());

        List<GroupEntity> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(group)
                .where(builder)
                .stream().count();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 정렬 조건 생성
     */
    private OrderSpecifier<?>[] createOrderSpecifiers(Pageable pageable) {
        return pageable.getSort().stream()
                .map(order -> {
                    String property = order.getProperty();
                    boolean isAsc = order.isAscending();

                    return switch (property) {
                        case "createdAt" -> isAsc ? group.createdAt.asc() : group.createdAt.desc();
                        case "updatedAt" -> isAsc ? group.updatedAt.asc() : group.updatedAt.desc();
                        case "title" -> isAsc ? group.title.asc() : group.title.desc();
                        case "participants" -> isAsc ? group.participants.asc() : group.participants.desc();
                        case "status" -> isAsc ? group.status.asc() : group.status.desc();
                        default -> group.createdAt.desc(); // 기본 정렬
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }

    /**
     * 문자열이 비어있지 않은지 확인
     */
    private boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }
}