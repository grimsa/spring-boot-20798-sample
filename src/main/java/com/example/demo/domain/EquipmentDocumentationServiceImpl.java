package com.example.demo.domain;

import com.example.demo.domain.entities.Project;

import java.util.List;

public class EquipmentDocumentationServiceImpl implements EquipmentDocumentationService {
    private final ProjectDao projectDao;

    public EquipmentDocumentationServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<String> something(Project project) {
        return List.of("asdad");
    }
}
