package com.example.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class TransactionalService that responsible for
 *
 * @author Mykola.Matsishin <br> created on 11 December 2020
 * @since 5.1
 */
@Service
public class TransactionalService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private MessageRepository messageRepository;

	@Transactional(transactionManager = "transactionManager")
	public void failToWriteTransaction(String key, String message) {
		kafkaTemplate.send("my_topic", key, message);
		throw new RuntimeException("there was error in transaction " + key);
	}

	@Transactional(transactionManager = "transactionManager")
	public void successToWriteTransaction(String key, String message) {
		kafkaTemplate.send("my_topic", key, message);
		System.out.println("successful commit of message with key " + key);
	}

	@Transactional(transactionManager = "chainedTm")
	public void combinedTransaction(String key, String message) {
		MessageDto messageDto = new MessageDto(key, message);
		messageRepository.save(messageDto);
		kafkaTemplate.send("my_topic", key, message);
		if ("failed".equals(message)) {
			throw new RuntimeException("failed");
		}
	}
}
