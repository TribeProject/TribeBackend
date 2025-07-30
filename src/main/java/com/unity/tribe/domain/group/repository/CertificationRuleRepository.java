package com.unity.tribe.domain.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.group.entity.CertificationRuleEntity;

@Repository
public interface CertificationRuleRepository extends JpaRepository<CertificationRuleEntity, Integer> {

    /**
     * 그룹 ID로 인증 규칙 조회
     */
    List<CertificationRuleEntity> findAllByGroupId(String groupId);

    /**
     * 그룹 ID로 가장 최근 인증 규칙 조회
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.groupId = :groupId ORDER BY c.createdAt DESC")
    Optional<CertificationRuleEntity> findLatestByGroupId(@Param("groupId") String groupId);

    /**
     * 그룹 ID 목록으로 인증 규칙 조회
     */
    List<CertificationRuleEntity> findByGroupIdIn(List<String> groupIds);

    /**
     * 그룹 ID와 인증 주기로 인증 규칙 조회
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.groupId = :groupId AND c.certificationFrequency = :frequency")
    List<CertificationRuleEntity> findByGroupIdAndCertificationFrequency(
            @Param("groupId") String groupId,
            @Param("frequency") String frequency);

    /**
     * 그룹 ID로 인증 규칙 존재 여부 확인
     */
    boolean existsByGroupId(String groupId);

    /**
     * 그룹 ID로 인증 규칙 삭제
     */
    void deleteByGroupId(String groupId);

    /**
     * 이미지 필수 인증 규칙 조회
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.imageRequired = 1")
    List<CertificationRuleEntity> findByImageRequiredTrue();

    /**
     * 텍스트 필수 인증 규칙 조회
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.textRequired = 1")
    List<CertificationRuleEntity> findByTextRequiredTrue();

    /**
     * 주간 인증 규칙 조회 (주간 인증 횟수가 있는 경우)
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.certificationFrequency = 'WEEKLY' AND c.weeklyCount IS NOT NULL")
    List<CertificationRuleEntity> findWeeklyRules();

    /**
     * 월간 인증 규칙 조회 (월간 인증 일자가 있는 경우)
     */
    @Query("SELECT c FROM CertificationRuleEntity c WHERE c.certificationFrequency = 'MONTHLY' AND c.monthlyDays IS NOT NULL")
    List<CertificationRuleEntity> findMonthlyRules();

    /**
     * 특정 목표와 연결된 인증 규칙 조회
     */
    @Query("SELECT c FROM CertificationRuleEntity c JOIN GoalEntity g ON c.certificationRuleId = g.certificationRuleId WHERE g.goalId = :goalId")
    Optional<CertificationRuleEntity> findByGoalId(@Param("goalId") Long goalId);
}