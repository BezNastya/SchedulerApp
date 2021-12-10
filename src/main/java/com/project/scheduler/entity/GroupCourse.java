package com.project.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GroupCourse  implements Comparable<GroupCourse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Course course;

    private byte groupNum;

    @ManyToMany(mappedBy = "groupCourse", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Student> students;

    @ManyToMany(mappedBy = "groupCourse", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Teacher> teachers;

    @OneToMany(mappedBy="lessonId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Lesson> lessons;

    public GroupCourse(byte groupNum){
        this.course = new Course();
        this.groupNum = groupNum;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    public GroupCourse(Course course, byte groupNum){
        this.course = course;
        this.groupNum = groupNum;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupCourse that = (GroupCourse) o;
        return groupNum == that.groupNum && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, groupNum);
    }

    @Override
    public int compareTo(GroupCourse o) {
        return Integer.compare(groupNum, o.getGroupNum());
    }
}
