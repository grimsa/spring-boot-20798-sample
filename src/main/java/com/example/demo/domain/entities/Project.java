package com.example.demo.domain.entities;

import javax.persistence.Entity;

@Entity
public class Project extends PersistentDomainObjectWithMetaData {

    public SpecificUser creator() {
        return new SpecificUser();
    }
}
