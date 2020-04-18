package com.example.demo.persistence.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class PersistentDomainObjectWithMetaData {
    @Temporal(TemporalType.TIMESTAMP)
    Date creationDate = new Date();

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
