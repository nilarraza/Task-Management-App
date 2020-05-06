package com.ToDoList.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoList.Model.Task;
import com.ToDoList.Model.User;
import com.ToDoList.Repository.TaskRepository;
import com.ToDoList.Repository.UserRepository;
import com.ToDoList.UService.MyUserDetails;
import com.ToDoList.UService.TaskService;

@CrossOrigin
@RestController
@EnableScheduling
@RequestMapping("app")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;

	
	@PostMapping("/createtask")
	public Task createTask(@RequestBody Task task, Authentication auth) {

		auth = SecurityContextHolder.getContext().getAuthentication();
		Object pricipal = auth.getPrincipal();
		String username = "";
		if (pricipal instanceof MyUserDetails) {
			username = ((MyUserDetails) pricipal).getUsername();
		}

		User user = userRepository.findByUsername(username);
		int id = user.getUserid();
		task.setUserId(id);

		System.out.println(task.getTime());
		taskRepository.save(task);

		return task;

	}

	public int currentUserId(Authentication auth) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		Object pricipal = auth.getPrincipal();
		String username = "";
		if (pricipal instanceof MyUserDetails) {
			username = ((MyUserDetails) pricipal).getUsername();
		}

		User user = userRepository.findByUsername(username);
		int id = user.getUserid();
		return id;

	}

	@GetMapping("todaytask")
	public Collection<Task> getTodayTask() {
		int userid = currentUserId(null);

		if (taskService.findTodayTasks(userid).isEmpty()) {
			return null;
		} else {
			return taskService.findTodayTasks(userid);
		}

	}
	
	
	
	@GetMapping("upcomingtask")
	public Collection<Task> UpcomingTask() {
		int userid = currentUserId(null);
		System.out.println("Working1");
		if (taskService.findUpcomingTasks(userid).isEmpty()) {
			System.out.println("Working4");
			return null;
		} else {
			return taskService.findUpcomingTasks(userid);
		}

	}
	
	@GetMapping("pasttask")
	public Collection<Task> PastTask() {
		int userid = currentUserId(null);
		System.out.println("Working1");
		if (taskService.findPastTasks(userid).isEmpty()) {
			System.out.println("Working4");
			return null;
		} else {
			return taskService.findPastTasks(userid);
		}

	}
	
	@Scheduled(cron = "0 0 6 * * ?")
	public void reminder() {
		System.out.println("Working notify");
		taskService.NotifyTasks();
	}


}
