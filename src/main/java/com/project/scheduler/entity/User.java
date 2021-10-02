package com.project.scheduler.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;


public abstract class User {


    @NotNull
    @Setter @Getter
    String email;

    @NotNull
    @Setter @Getter
    String password;

}
