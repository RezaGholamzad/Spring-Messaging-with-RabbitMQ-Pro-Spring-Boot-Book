package com.prospringboot.messagingRabbitMQ.rmq;

import com.prospringboot.messagingRabbitMQ.domain.ToDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    @EnableScheduling. This annotation tells (via auto-configuration)
    the Spring container that the org.springframework.scheduling.
    annotation.ScheduledAnnotationBeanPostProcessor class
    needs to be created. It registers all the methods annotated
    with @Scheduled to be invoked by an org.springframework.
    scheduling.TaskScheduler interface implementation according
    to the fixedRate, fixedDe
 */
@EnableScheduling
@Configuration
public class ToDoSender {

    private final ToDoProducer toDoProducer;

    public ToDoSender(ToDoProducer toDoProducer) {
        this.toDoProducer = toDoProducer;
    }

    @Value("${todo.amqp.queue}")
    private String destination;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*
        @Scheduled(fixedDelay = 500L). This annotation tells the
        TaskScheduler interface implementation to execute the sendToDos
        method with a fixed delay of 500 milliseconds. This means that every
        half second you send a message to the queue.
     */
    @Scheduled(fixedRate = 500L)
    private void sendTo(){
        toDoProducer.sendTo(destination,
                new ToDo("Thinking on Spring Boot at " + dateFormat.format(new Date())));
    }

    /*
        RabbitMQ UI management :
            http://localhost:15672
            user and password = guest
     */
}
