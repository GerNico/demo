package com.example.kafka.demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class RestController that responsible for
 *
 * @author Mykola.Matsishin <br> created on 29 November 2020
 * @since 5.0
 */
@RestController
public class TestController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, DtoExample> greetingKafkaTemplate;

	@PostMapping("/publish")
	public void publishMessage(@RequestBody String message) {
		kafkaTemplate.send("my_topic", message);
	}

	@PostMapping("/publish/dto")
	public void publishMessage(@RequestBody DtoExample message) {
		greetingKafkaTemplate.send("my_topic2", message.getId().toString(), message);
	}

	@PutMapping("/publish/dto/{id}")
	public void updateMessage(@RequestBody DtoExample message, @PathVariable String id) {
		UUID key = UUID.fromString(id);
		message.setId(key);
		greetingKafkaTemplate.send("my_topic2", key.toString(), message);
	}

	@DeleteMapping("/publish/dto/{id}")
	public void deleteMessage(@PathVariable String id) {
		UUID key = UUID.fromString(id);
		greetingKafkaTemplate.send("my_topic2", key.toString(), null);
	}

	@KafkaListener(topics = "my_topic", groupId = "groupId")
	public void listenGroupFoo(String message) {
		System.out.println("Received plain String Message : " + message);
	}

	@KafkaListener(topics = "my_topic2", groupId = "groupId2", containerFactory = "greetingKafkaListenerContainerFactory")
	public void listenGroupFoo(@Payload(required = false) DtoExample message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		System.out.println("Received dto Message: key =" + key + " value=" + message);
	}
}
