package com.example.demo.domain.entities;

import com.example.demo.persistence.entity.PersistentDomainObjectWithMetaData;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.FIELD)
public class Segment extends PersistentDomainObjectWithMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrivateOwned
    @CascadeOnDelete
    private BaseUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
