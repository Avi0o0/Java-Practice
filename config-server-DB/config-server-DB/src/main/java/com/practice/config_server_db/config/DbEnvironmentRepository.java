package com.practice.config_server_db.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import com.practice.config_server_db.entity.PropertyEntity;
import com.practice.config_server_db.repo.PropertyRepository;

@Component
public class DbEnvironmentRepository implements EnvironmentRepository {

	private final PropertyRepository propertyRepository;

	public DbEnvironmentRepository(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	@Override
	public Environment findOne(String application, String profile, String label) {
		if (profile == null || profile.isEmpty())
			profile = "default";
		if (label == null || label.isEmpty())
			label = "master";

		Environment environment = new Environment(application, profile, label);

		List<PropertyEntity> properties = propertyRepository.findByApplicationAndProfileAndLabel(application, profile,
				label);

		Map<String, Object> propertyMap = new HashMap<>();
		for (PropertyEntity prop : properties) {
			String value = prop.getPropValue();
			// 🔹 decrypt if encrypted
			if (Boolean.TRUE.equals(prop.getEncrypted())) {
				value = decryptValue(value);
			}
			propertyMap.put(prop.getPropKey(), value);
			System.out.println("Key:" + prop.getPropKey() + " Value: " + value );
		}

		environment.add(new PropertySource("database", propertyMap));
		return environment;
	}

	// 🔑 Dummy decrypt method (replace with Feign client or Jasypt)
	private String decryptValue(String encryptedValue) {
		// TODO: connect to your decryption MS or use Jasypt
		return "DECRYPTED(" + encryptedValue + ")";
	}
}
