package com.example.demo.domain;

import com.example.demo.domain.a.SomeManager;
import com.example.demo.domain.entities.BaseUser;
import com.example.demo.domain.entities.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectManagerImpl implements SomeManager<Project> {
    @Override
    public void doStuff(Project withThis) {
        System.out.println("Doing something" + withThis.getCreationDate());
        doSomethingWithOtherEntity(withThis.creator());
    }

    public void doSomethingWithOtherEntity(BaseUser e) {
        System.out.println(e.greeting());
    }
}
