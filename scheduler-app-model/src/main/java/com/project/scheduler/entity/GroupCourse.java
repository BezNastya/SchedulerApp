package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class GroupCourse implements Comparable<GroupCourse> {
    @Id
    private Long id;

    private byte groupNum;

    @ManyToMany(mappedBy = "groupCourse", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Student> students;

    @ManyToMany(mappedBy = "groupCourse", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Teacher> teachers;

    public GroupCourse(byte groupNum){
        this.groupNum = groupNum;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupCourse that = (GroupCourse) o;
        return Objects.equals(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public int compareTo(GroupCourse o) {
        return Integer.compare(groupNum, o.getGroupNum());
    }
}
