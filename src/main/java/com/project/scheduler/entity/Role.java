package com.project.scheduler.entity;

public enum Role {
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

    public String getRoleStringRepresentation() {return role;}

}
