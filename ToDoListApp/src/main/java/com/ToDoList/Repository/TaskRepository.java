package com.ToDoList.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ToDoList.Model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByUserId(int userid);

	List<Task> findByDate(Date date);

	@Query("select a from Task a  where date < :tdate")
	List<Task> findPastDate(Date tdate);

	@Query("select a from Task a where date > :tdate")
	List<Task> findUpcomingDate(Date tdate);

	@Query("select userId from Task where date = :tdate group by userId")
	List<Integer> finduserIds(Date tdate);

	@Query("select a from Task a where date = :tdate and userId = :userid")
	List<Task> findByIdandDate(Date tdate,int userid);

}
