package com.example.kafka.demo;/*
 * Copyright (c) 2020 AVI-SPL Inc.
 * All Rights Reserved.
 */

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Class KafkaTopicConfig that responsible for
 *
 * @author Mykola.Matsishin <br> created on 27 November 2020
 * @since 5.0
 */
@Configuration
@EnableTransactionManagement
public class KafkaConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	@Value(value = "${kafka.topic}")
	private String topic;
	@Value(value = "${kafka.topic.numPartitions}")
	private String numPartitions;
	@Value(value = "${kafka.topic.replicationFactor}")
	private String replicationFactor;
	@Value(value = "${kafka.topic2}")
	private String topic2;
	@Value(value = "${kafka.topic.numPartitions2}")
	private String numPartitions2;
	@Value(value = "${kafka.topic.replicationFactor2}")
	private String replicationFactor2;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic1() {
		return new NewTopic(topic, Integer.parseInt(numPartitions), Short.parseShort(replicationFactor));
	}

	@Bean
	public NewTopic topic2() {
		return new NewTopic(topic2, Integer.parseInt(numPartitions2), Short.parseShort(replicationFactor2));
	}


	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(configProps);
		factory.transactionCapable();
		factory.setTransactionIdPrefix("tran-");
		return factory;
	}

	@Bean
	public KafkaTransactionManager<String,String> transactionManager() {
		return new KafkaTransactionManager<>(producerFactory());
	}


	@Bean
	public ChainedKafkaTransactionManager<Object, Object> chainedTm(KafkaTransactionManager<String, String> ktm,
			DataSourceTransactionManager dstm) {

		return new ChainedKafkaTransactionManager<>(ktm, dstm);
	}

	@Bean
	public DataSourceTransactionManager dstm(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, DtoExample> greetingProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, DtoExample> greetingKafkaTemplate() {
		return new KafkaTemplate<>(greetingProducerFactory());
	}
}
