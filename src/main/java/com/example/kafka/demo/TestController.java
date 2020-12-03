package com.example.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
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
		greetingKafkaTemplate.send("my_topic2", message);
	}

	@KafkaListener(topics = "my_topic", groupId = "groupId")
	public void listenGroupFoo(String message) {
		System.out.println("Received plain String Message : " + message);
	}

	@KafkaListener(topics = "my_topic2", groupId = "groupId2", containerFactory = "greetingKafkaListenerContainerFactory")
	public void listenGroupFoo(DtoExample message) {
		System.out.println("Received dto Message: " + message);
	}
}
