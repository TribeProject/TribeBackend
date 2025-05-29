package com.unity.tribe.domain.group.entity;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "goal_unit_type", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalUnitTypeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "unit_type_id")
    private Integer unitTypeId;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoalUnitTypeEntity that = (GoalUnitTypeEntity) o;
        return Objects.equals(unitTypeId, that.unitTypeId)
                && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitTypeId, title);
    }
}
