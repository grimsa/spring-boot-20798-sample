package com.example.demo.domain.entities;

import com.example.demo.persistence.entity.PersistentDomainObjectWithMetaData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Project")
@Table(name = "project")
public class Project extends PersistentDomainObjectWithMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpecificUser creator() {
        return new SpecificUser();
    }
}
