package com.prospringboot.messagingRabbitMQ;

import com.prospringboot.messagingRabbitMQ.domain.ToDo;
import com.prospringboot.messagingRabbitMQ.rmq.ToDoProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMessagingWithRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMessagingWithRabbitmqApplication.class, args);
    }

    @Bean
    public CommandLineRunner sendToDos(@Value("${todo.amqp.queue}") String destination, ToDoProducer producer){
        return args -> {
            producer.sendTo(destination, new ToDo("workout tomorrow morning!"));
        };
    }

}
