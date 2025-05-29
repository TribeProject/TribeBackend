package com.unity.tribe.domain.group.entity;

import java.util.ArrayList;
import java.util.List;

import com.unity.tribe.common.model.BaseEntity;

import jakarta.persistence.*;
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

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
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

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<GroupEntity> groups = new ArrayList<>();

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