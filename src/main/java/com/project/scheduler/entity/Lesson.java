package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Lesson implements Comparable<Lesson>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lessonId;

    @Enumerated(EnumType.ORDINAL)
    private LessonType type;

    private String place;

    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride( name = "dayOfTheWeek", column = @Column(name = "scheduleDate_dayOfTheWeek")),
//            @AttributeOverride( name = "lessonOrder", column = @Column(name = "scheduleDate_lessonOrder")),
//            @AttributeOverride( name = "week", column = @Column(name = "scheduleDate_week"))
//    })
    private ScheduleDate date;

    @ManyToOne
    @JoinColumn
    private GroupCourse groupCourse;

    @OneToOne(mappedBy = "canceledLesson", cascade = CascadeType.REMOVE)
    private PostponeLesson postponeLesson;

    public Lesson(LessonType type, String place, ScheduleDate date, GroupCourse groupCourse){
        this.type = type;
        this.place = place;
        this.date = date;
        this.groupCourse = groupCourse;
    }
    public Lesson(LessonType type, String place, ScheduleDate date){
        this.type = type;
        this.place = place;
        this.date = date;
    }

    @Override
    public int compareTo(Lesson that) {
        if (this.equals(that))
            return 0;
        int compareDates = this.date.compareTo(that.date);
        if (compareDates != 0)
            return compareDates;
        int compareNames = this.groupCourse.getCourse().getName().compareTo(that.groupCourse.getCourse().getName());
        if (compareNames != 0)
            return compareNames;
        return Integer.compare(this.groupCourse.getGroupNum(), that.groupCourse.getGroupNum());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return type == lesson.type && Objects.equals(place, lesson.place) && Objects.equals(date, lesson.date) && Objects.equals(groupCourse, lesson.groupCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, place, date, groupCourse);
    }
}
