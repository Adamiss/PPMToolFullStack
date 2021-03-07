package com.adam.ppmtool.services;

import com.adam.ppmtool.domain.Backlog;
import com.adam.ppmtool.domain.Project;
import com.adam.ppmtool.domain.ProjectTask;
import com.adam.ppmtool.exceptions.ProjectNotFoundException;
import com.adam.ppmtool.repositories.BacklogRepository;
import com.adam.ppmtool.repositories.ProjectRepository;
import com.adam.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the bl to pt
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
            Integer BacklogSequence = backlog.getPTSequence();
            // Update the BL SEQUENCE
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null

            //INITIAL status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            if (projectTask.getPriority() == null) { //In the future we need projectTask.getPriority()== 0 to handle the form
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not Found");
        }
    }



    public Iterable<ProjectTask>findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
             throw new ProjectNotFoundException("Project with ID: '"+id.toString()+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public  ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        //TODO: make sure that we are searching on the right backlog

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog==null){
            throw new ProjectNotFoundException("Project with ID ='"+backlog_id.toString()+"' does not exist");
        }

        //TODO: make sure that our tasks exist

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project task '"+pt_id.toString()+"' not found");
        }

        //TODO: make sure that the backlog/project id in the path corresponds to the right project

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: "+ backlog_id);
        }

        return projectTask;
    }

    //Update project task

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deletePTByProjectSequence (String backlog_id, String pt_id){

        ProjectTask projectTask =findPTByProjectSequence(backlog_id, pt_id);

        //Should be fixed
/*        Backlog backlog = projectTask.getBacklog();
        List<ProjectTask> pts = backlog.getProjectTasks();
        pts.remove(projectTask);
        backlogRepository.save(backlog);*/

        projectTaskRepository.delete(projectTask);

    }

    //Todo: find existing project task

    //Todo: replace it with updated task

    //Todo: save update

}
