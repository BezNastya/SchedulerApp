package com.project.scheduler.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


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
    @NotBlank(message = "Every user must have an email")
    @Email
    @Setter
    @Getter
    String email;

    @NotNull
    @NotBlank(message = "Every user must have a password")
    @Setter
    @Getter
    String password;

    @NotNull
    @NotBlank(message = "Every user must have a first name")
    @Setter
    @Getter
    private String firstName;

    @NotNull
    @NotBlank(message = "Every user must have a last name")
    @Setter
    @Getter
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    @Setter
    @Getter
    private Role role;

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

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

}
