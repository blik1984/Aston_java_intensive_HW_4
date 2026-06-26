package com.homework.four.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.homework.four.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

}
