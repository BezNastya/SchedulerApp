package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Objects;

@Data
//@Entity
//@Table(name = "master")
public class Admin {
//    @Column(name = "id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


//    @Column(name="username")
     @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id && username.equals(admin.username) && password.equals(admin.password) && passwordConfirm.equals(admin.passwordConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, passwordConfirm);
    }
}