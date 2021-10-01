package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Objects;

@Data
//@Entity
//@Table(name = "master")
public class Admin extends User{
//    @Column(name = "id")
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}