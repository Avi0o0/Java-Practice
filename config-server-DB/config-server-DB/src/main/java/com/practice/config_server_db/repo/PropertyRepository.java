package com.practice.config_server_db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.config_server_db.entity.PropertyEntity;

import java.util.List;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
	List<PropertyEntity> findByApplicationAndProfileAndLabel(String application, String profile, String label);
}
