package com.practice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.app.entity.EmailEntity;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Integer>{

}
