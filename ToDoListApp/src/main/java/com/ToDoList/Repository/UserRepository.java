package com.ToDoList.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ToDoList.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String userName);

	User findByuserid(int userId);

	List<User> findByRole(String string);
}
