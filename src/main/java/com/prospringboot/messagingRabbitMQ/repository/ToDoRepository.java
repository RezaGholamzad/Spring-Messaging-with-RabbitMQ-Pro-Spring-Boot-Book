package com.prospringboot.messagingRabbitMQ.repository;

import com.prospringboot.messagingRabbitMQ.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, String> {
}
