package com.example.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
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

	@KafkaListener(topics = "my-topic2", groupId = "sasl.scram.consumer", containerFactory = "greetingKafkaListenerContainerFactory")
	public void listenGroupFoo(@Payload(required = false) String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		System.out.println("Key: " + key + " Received plain String Message : " + message);
	}

	@PostMapping("/publishTransaction/success/{key}")
	public void succeedToPublishMessage(@PathVariable String key, @RequestBody String message) {
		kafkaTemplate.send("my-topic2", key, message);
		System.out.println("successful commit of message with key " + key);
	}
}
