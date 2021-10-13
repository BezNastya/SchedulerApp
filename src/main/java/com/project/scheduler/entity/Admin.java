package com.project.scheduler.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User{

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    */
    /*
    @MapsId
    @OneToOne(mappedBy = "admin")
    @JoinColumn(name = "id")
    private User user;
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return userId == admin.userId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(userId);
    }
}