package com.prospringboot.messagingRabbitMQ.rmq;

import com.prospringboot.messagingRabbitMQ.domain.ToDo;
import com.prospringboot.messagingRabbitMQ.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ToDoConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(ToDoConsumer.class);

    private final ToDoRepository repository;

    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    /*
        @RabbitListener. This annotation marks the method (because you
        can use this annotation in a class as well) for creating a handler for
        any incoming messages, meaning that it creates a listener that is
        connected to the RabbitMQ’s queue and passes that message to the
        method. Behind the scenes, the listener does its best to convert the
        message to the appropriate type by using the right message converter
        (an implementation of the ­ org.springframework.amqp.support.
        converter.MessageConverter interface. This interface belongs to the
        spring-amqp project); in this case, it converts from JSON to a ToDo
        instance.
     */
    @RabbitListener(queues = "${todo.amqp.queue}")
    public void processToDo(ToDo toDo){
        LOG.info("consumer> " + toDo);
        LOG.info("Todo created> " + this.repository.save(toDo));
    }
}
