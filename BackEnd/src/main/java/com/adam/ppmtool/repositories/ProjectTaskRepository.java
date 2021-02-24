package com.adam.ppmtool.repositories;

import com.adam.ppmtool.domain.Backlog;
import com.adam.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {
}
