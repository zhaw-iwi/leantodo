package ch.zhaw.sml.iwi.meng.leantodo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.sml.iwi.meng.leantodo.entity.Project;
import ch.zhaw.sml.iwi.meng.leantodo.entity.ProjectRepository;
import ch.zhaw.sml.iwi.meng.leantodo.entity.ToDo;

@Component
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> listAllProjects(String loginName) {
        return projectRepository.findByOwner(loginName);
    }

    public void persistProject(Project newProject, String owner) {
        newProject.setId(null);
        // We only create empty projects
        newProject.getToDos().clear();
        newProject.setOwner(owner);
        projectRepository.save(newProject);
    }

    public void addToDo(Long projectId, ToDo toDo, String owner) {
        if(projectId == null) {
            return;
        }
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty() || !projectOpt.get().getOwner().equals(owner)) {
            return;
        } else {
            Project p = projectOpt.get();
            // Ensure that JPA creates a new entity
            toDo.setId(null);
            toDo.setOwner(owner);
            p.getToDos().add(toDo);
            projectRepository.save(p);
        }

    }

}