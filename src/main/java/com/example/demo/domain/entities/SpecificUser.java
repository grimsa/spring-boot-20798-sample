package com.example.demo.domain.entities;

import javax.persistence.Entity;

@Entity
public class SpecificUser extends BaseUser {
    public String greeting() {
        return "Hello from SpecificUser";
    }
}
