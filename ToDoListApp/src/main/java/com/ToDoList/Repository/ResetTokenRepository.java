package com.ToDoList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ToDoList.Model.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Integer>{

	public ResetToken findByToken(String token);



}
