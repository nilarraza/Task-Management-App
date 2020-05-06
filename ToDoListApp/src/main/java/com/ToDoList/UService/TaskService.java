package com.ToDoList.UService;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ToDoList.Model.Task;
import com.ToDoList.Model.User;
import com.ToDoList.Repository.TaskRepository;
import com.ToDoList.Repository.UserRepository;
import com.ToDoList.Util.EmailUtil;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	EmailUtil email;

	@Autowired
	UserRepository userRepository;

	public Collection<Task> findTodayTasks(int userid) {

		ArrayList<Task> tasks = new ArrayList<Task>();

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);

		for (Task tasksingle : taskRepository.findByDate(date)) {

			if (userid == tasksingle.getUserId()) {
				tasks.add(tasksingle);
				System.out.println("if working");
			}
		}
		return tasks;

	}

	public Collection<Task> findPastTasks(int userid) {

		ArrayList<Task> tasks = new ArrayList<Task>();

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		System.out.println("Working2");
		for (Task task : taskRepository.findPastDate(date)) {
			System.out.println("Working3");
			if (userid == task.getUserId()) {
				tasks.add(task);
				System.out.println("if working");
			}
		}
		return tasks;

	}

	public Collection<Task> findUpcomingTasks(int userid) {

		ArrayList<Task> tasks = new ArrayList<Task>();

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		System.out.println("Working2");
		for (Task tasksingle : taskRepository.findUpcomingDate(date)) {
			System.out.println("Working3");
			if (userid == tasksingle.getUserId()) {
				tasks.add(tasksingle);
				System.out.println("if working");
			}
		}
		return tasks;

	}

	public void NotifyTasks() {

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
//		System.out.println("Working2");

		ArrayList<Task> tasks = new ArrayList<Task>();

		for (int id : taskRepository.finduserIds(date)) {
			User user = userRepository.findByuserid(id);

			for (Task task : taskRepository.findByIdandDate(date, id)) {
				tasks.add(task);
			}

			String[] str = new String[tasks.size()];
			//Iterator<Task> itr = tasks.iterator();

			for (int i = 0; i < tasks.size(); i++) {
//				Task ts = (Task) itr.next();

				String tsString = "\n Title : "+tasks.get(i).getTitle() + " Description : " + tasks.get(i).getDescription()
						+ " Date : " + tasks.get(i).getDate() + " Time : " + tasks.get(i).getTime();
				str[i] = tsString;
			}

			String joinedString = String.join(", ", str);
			System.out.println(joinedString);
			email.sendEmail(user.getEmail(), "Today Tasks", "Your Today Tasks\n" + joinedString);

		}

	}

}
