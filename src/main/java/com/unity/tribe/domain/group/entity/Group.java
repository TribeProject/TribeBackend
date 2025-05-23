package com.unity.tribe.domain.group.entity;

import java.util.ArrayList;
import java.util.List;

import com.unity.tribe.common.model.BaseEntity;
import com.unity.tribe.common.model.enums.GroupStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private GroupCategory category;

    @Column(nullable = false)
    private Integer maxMembers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupStatus status;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Goal> goals = new ArrayList<>();

    @Builder
    public Group(String name, String description, String imageUrl, GroupCategory category, Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.maxMembers = maxMembers;
        this.status = GroupStatus.WAITING;
    }

    public void update(String name, String description, String imageUrl, GroupCategory category, Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.maxMembers = maxMembers;
    }

    public void updateStatus(GroupStatus status) {
        this.status = status;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void removeMember(Member member) {
        this.members.remove(member);
    }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }

    public void removeGoal(Goal goal) {
        this.goals.remove(goal);
    }

    public boolean isFull() {
        return this.members.size() >= this.maxMembers;
    }
}