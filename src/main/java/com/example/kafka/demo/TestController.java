package com.example.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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
	private TransactionalService transactionalService;


	@KafkaListener(topics = "sasl-tsl-topic", groupId = "main")
	public void listenGroupFoo(String message) {
		System.out.println("Received plain String Message : " + message);
	}

	@PostMapping("/publishTransaction/fail/{key}")
	public void failToPublishMessage(@PathVariable String key, @RequestBody String message) {
		transactionalService.failToWriteTransaction(key, message);
	}

	@PostMapping("/publishTransaction/success/{key}")
	public void succeedToPublishMessage(@PathVariable String key, @RequestBody String message) {
		transactionalService.successToWriteTransaction(key, message);
	}
}
