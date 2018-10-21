/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.repository;

import com.tasker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
