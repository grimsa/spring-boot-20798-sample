package com.example.demo.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BaseUser extends PersistentDomainObjectWithMetaData {

    public String greeting() {
        return "Hello from BaseUser";
    }
}
