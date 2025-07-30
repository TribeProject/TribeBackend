package com.unity.tribe.domain.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.group.entity.GroupEntity;

/**
 * 그룹 Repository
 * JpaRepository와 Custom Repository를 상속
 */
@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, String>, GroupRepositoryCustom {

    /**
     * 상태별 그룹 조회
     */
    Page<GroupEntity> findAllByStatus(GroupEntity.GroupStatus status, Pageable pageable);

    /**
     * 카테고리별 그룹 조회
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.categoryCode = :categoryCode AND (:status IS NULL OR g.status = :status)")
    Page<GroupEntity> findAllByCategoryCodeAndStatus(@Param("categoryCode") String categoryCode,
            @Param("status") GroupEntity.GroupStatus status,
            Pageable pageable);

    /**
     * 호스트별 그룹 조회
     */
    List<GroupEntity> findAllByHostId(String hostId);

    /**
     * 호스트별 활성 그룹 조회
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.hostId = :hostId AND g.status != 'DISBANDED'")
    List<GroupEntity> findActiveGroupsByHostId(@Param("hostId") String hostId);

    /**
     * 제목으로 그룹 검색
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.title LIKE %:title% AND g.status != 'DISBANDED'")
    List<GroupEntity> findByTitleContainingIgnoreCase(@Param("title") String title);

    /**
     * 지역별 그룹 조회
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.locationAddress LIKE %:region% AND g.status != 'DISBANDED'")
    List<GroupEntity> findByRegion(@Param("region") String region);

    /**
     * 참여 가능한 그룹 조회 (정원에 여유가 있는 그룹)
     * 실제로는 Member 테이블과 조인해서 현재 인원을 계산해야 하지만, 여기서는 기본 구조만 제시
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.status = 'WAITING' OR g.status = 'ONGOING'")
    Page<GroupEntity> findJoinableGroups(Pageable pageable);

    /**
     * 카테고리별 활성 그룹 수 조회
     */
    @Query("SELECT COUNT(g) FROM GroupEntity g WHERE g.categoryCode = :categoryCode AND g.status != 'DISBANDED'")
    long countActiveByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * 특정 기간 내 생성된 그룹 조회
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.createdAt BETWEEN :startDate AND :endDate")
    List<GroupEntity> findGroupsCreatedBetween(@Param("startDate") java.sql.Timestamp startDate,
            @Param("endDate") java.sql.Timestamp endDate);

    /**
     * 인기 그룹 조회 (참여자 수 기준)
     * 실제로는 현재 멤버 수를 계산해야 하지만, 여기서는 최대 정원 기준으로 임시 구현
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.status != 'DISBANDED' ORDER BY g.participants DESC")
    Page<GroupEntity> findPopularGroups(Pageable pageable);

    /**
     * 만료 예정 그룹 조회
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.expiredAt IS NOT NULL AND g.expiredAt <= :threshold AND g.status != 'DISBANDED'")
    List<GroupEntity> findExpiringGroups(@Param("threshold") java.sql.Timestamp threshold);

    /**
     * 그룹 존재 여부 확인 (ULID 기반)
     */
    boolean existsByGroupId(String groupId);

    /**
     * 그룹 ID로 상세 조회 (선택적)
     */
    @Query("SELECT g FROM GroupEntity g WHERE g.groupId = :groupId")
    Optional<GroupEntity> findByGroupId(@Param("groupId") String groupId);
}