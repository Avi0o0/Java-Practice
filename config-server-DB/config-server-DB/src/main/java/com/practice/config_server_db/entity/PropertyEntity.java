package com.practice.config_server_db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
public class PropertyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String application;
	private String profile;
	private String label;

	@Column(name = "key")
	private String propKey;

	@Column(name = "value")
	private String propValue;

	@Column(name = "created_at", updatable = false, insertable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	private Boolean encrypted;

	// getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}
}
