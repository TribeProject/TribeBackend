package com.unity.tribe.domain.group.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unity.tribe.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "group_categories")
@Getter
public class GroupCategory extends BaseEntity {

    public enum GroupCategoryCode {
        FOOD("F", "FOOD"),
        TRIP("T", "TRIP"),
        EXERCISE_AND_ACTIVITY("EA", "EXERCISE_AND_ACTIVITY"),
        CULTURE_AND_ART("CA", "CULTURE_AND_ART"),
        SELF_DEVELOPMENT("SD", "SELF_DEVELOPMENT"),
        GAME("G", "GAME"),
        BEAUTY_AND_FASHION("BF", "BEAUTY_AND_FASHION"),
        VOLUNTEER("V", "VOLUNTEER");

        private final String code;
        private final String description;

        GroupCategoryCode(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @JsonValue
        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        /**
         * JSON에서 code로 enum 찾기
         * 
         * @param code 찾을 코드 문자열 (e.g. "EA", "F")
         * @return 해당하는 GroupCategoryCode
         * @throws IllegalArgumentException 해당하는 코드가 없을 경우
         */
        @JsonCreator
        public static GroupCategoryCode fromCode(String code) {
            for (GroupCategoryCode categoryCode : values()) {
                if (categoryCode.getCode().equals(code)) {
                    return categoryCode;
                }
            }
            throw new IllegalArgumentException("Invalid category code: " + code);
        }

        /**
         * 코드 문자열로 GroupCategoryCode를 찾습니다.
         * 
         * @param code 찾을 코드 문자열 (e.g. "EA", "F")
         * @return 해당하는 GroupCategoryCode
         * @throws IllegalArgumentException 해당하는 코드가 없을 경우
         */
        public static GroupCategoryCode findByCode(String code) {
            return fromCode(code); // 기존 메서드는 fromCode 호출
        }
    }

    protected GroupCategory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private GroupCategoryCode code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Builder
    public GroupCategory(GroupCategoryCode code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}