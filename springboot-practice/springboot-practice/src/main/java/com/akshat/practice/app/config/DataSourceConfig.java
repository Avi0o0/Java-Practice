package com.akshat.practice.app.config;

import javax.sql.DataSource;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jasypt.security.exception.PasswordEncryptionException;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	private static BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

	public DataSourceConfig() {
		String jasyptPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
		if (jasyptPassword == null || jasyptPassword.isEmpty()) {
			throw new PasswordEncryptionException("JASYPT_ENCRYPTOR_PASSWORD environment variable not set");
		}
		textEncryptor.setPassword(jasyptPassword);
	}

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String encryptedPassword;

	@Bean
	DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(url);
		ds.setUsername(username);
		ds.setPassword(decrypt(encryptedPassword));
		return ds;
	}

	public static String decrypt(String value) {
		
		if (value.startsWith("ENC(") && value.endsWith(")")) {
            value = value.substring(4, value.length() - 1);
        }
		
		try {
			return textEncryptor.decrypt(value);
		} catch (Exception e) {
			throw new PasswordEncryptionException("Error decrypting password", e);
		}
	}
}
