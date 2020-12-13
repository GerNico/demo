package com.example.kafka.demo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class MessageRepository that responsible for
 *
 * @author Mykola.Matsishin <br> created on 13 December 2020
 * @since 5.1
 */
public interface MessageRepository extends JpaRepository<MessageDto, String> {
}
