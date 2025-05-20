package com.unity.tribe.common.group.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "goal", schema = "tribe", catalog = "")
public class GoalEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "group_id")
    private String groupId;
    @Basic
    @Column(name = "Field2")
    private String field2;
    @Basic
    @Column(name = "Field3")
    private String field3;
    @Basic
    @Column(name = "Field4")
    private String field4;
    @Basic
    @Column(name = "Field5")
    private String field5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalEntity that = (GoalEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(groupId, that.groupId) && Objects.equals(field2, that.field2) && Objects.equals(field3, that.field3) && Objects.equals(field4, that.field4) && Objects.equals(field5, that.field5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, field2, field3, field4, field5);
    }
}
