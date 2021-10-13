package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@ToString
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected long userId;

    @NotNull
    @Setter
    @Getter
    String email;

    @NotNull
    @Setter
    @Getter
    String password;

    @NotNull
    @Setter
    @Getter
    private String firstName;

    @NotNull
    @Setter
    @Getter
    private String lastName;


    @NotNull
    @Setter
    @Getter
    private String role;

    @NotNull
    @Setter
    @Getter
    private boolean authorized;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(userId);
    }

}
