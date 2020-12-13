package com.example.kafka.demo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Class MessageDto that responsible for
 *
 * @author Mykola.Matsishin <br> created on 13 December 2020
 * @since 5.1
 */
@Entity
@Table(schema = "public", name = "messages")
public class MessageDto {
	@Id
	@Column(name = "key")
	private String key;
	@Column(name = "message")
	private String message;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageDto() {
	}

	public MessageDto(String key, String message) {
		this.key = key;
		this.message = message;
	}
}
