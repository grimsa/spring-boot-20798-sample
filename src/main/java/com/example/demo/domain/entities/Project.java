package com.example.demo.domain.entities;

import com.example.demo.persistence.entity.PersistentDomainObjectWithMetaData;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "Project")
@Table(name = "project")
public class Project extends PersistentDomainObjectWithMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="parent", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    @PrivateOwned
    @CascadeOnDelete
    private List<WorkItem> workItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public SpecificUser creator() {
        return new SpecificUser();
    }
}
