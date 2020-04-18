package com.example.demo.domain.beans;

import com.example.demo.domain.entities.BaseUser;
import com.example.demo.domain.entities.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectManager {
    public void doStuff(Project withThis) {
        doSomething(withThis.creator());
    }

    private void doSomething(BaseUser e) {
        System.out.println(e.greeting());
    }
}
