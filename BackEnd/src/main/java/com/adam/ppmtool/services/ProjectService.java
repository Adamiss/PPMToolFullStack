package com.adam.ppmtool.services;

import com.adam.ppmtool.domain.Backlog;
import com.adam.ppmtool.domain.Project;
import com.adam.ppmtool.exceptions.ProjectIdException;
import com.adam.ppmtool.repositories.BacklogRepository;
import com.adam.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        }
        catch (Exception e){
            throw new ProjectIdException("Project ID '"+  project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw  new ProjectIdException("Project ID '"+ projectId.toUpperCase()+"' doesn't exists");
        }
        return project;
    }

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Cannot delete project with ID '"+projectId.toUpperCase()+"'. The project doesn't exists");
        }
         projectRepository.delete(project);
    }

}
