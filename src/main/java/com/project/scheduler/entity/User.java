package com.project.scheduler.entity;

import lombok.Getter;
import lombok.Setter;

public abstract class User {

    String email;
    @Setter @Getter
    String password;

    abstract void registerUser();

}
