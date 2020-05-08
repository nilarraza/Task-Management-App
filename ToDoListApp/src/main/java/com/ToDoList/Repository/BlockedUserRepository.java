package com.ToDoList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ToDoList.Model.BlockedUser;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Integer> {

	BlockedUser findByuserid(int id);

	

}
