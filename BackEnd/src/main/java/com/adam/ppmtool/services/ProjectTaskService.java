package com.adam.ppmtool.services;

import com.adam.ppmtool.domain.Backlog;
import com.adam.ppmtool.domain.ProjectTask;
import com.adam.ppmtool.repositories.BacklogRepository;
import com.adam.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private BacklogRepository backlogRepository;

    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        // TODO: Exception: Project NOT FOUND

        // DONE: PTs to be added to a specific project, project !=null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // DONE: set the BL to PT
        projectTask.setBacklog(backlog);
        // DONE: We want our project sequence to be like this: IDPR0-1,  IDPR0-2 ..... IDPR0-1000
        Integer BacklogSequence = backlog.getPTSequence();
        // DONE: Update the BL sequence
        BacklogSequence++;
        // DONE: Add sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        // DONE: INITIAL priority when priority null
        if(projectTask.getPriority() == 0 || projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }
        // DONE: INITIAL status when status is null
        if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }


        return projectTaskRepository.save(projectTask);
    }

}
