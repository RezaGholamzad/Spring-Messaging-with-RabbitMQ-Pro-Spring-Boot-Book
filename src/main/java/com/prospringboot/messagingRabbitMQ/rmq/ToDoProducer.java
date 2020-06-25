package com.prospringboot.messagingRabbitMQ.rmq;

import com.prospringboot.messagingRabbitMQ.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ToDoProducer {
    private static final Logger LOG = LoggerFactory.getLogger(ToDoProducer.class);

    /*
        RabbitTemplate. The RabbitTemplate is a helper class that simplifies
        synchronous/asynchronous access to RabbitMQ for sending and/or
        receiving messages.
     */
    private final RabbitTemplate rabbitTemplate;

    public ToDoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /*
        This method has the routing key
        and the message as parameters. In this case, the routing key is the
        name of the queue. This method uses the rabbitTemplate instance
        to call the convertAndSend method that accepts the routing key and
        the message. Remember that the message is sent to the exchange
        (the default exchange) and the exchange routes the message to the
        right queue. This routing key happens to be the name of the queue.
        Also remember that by default RabbitMQ always binds the default
        exchange (Direct Exchange) to a queue and the routing key is the
        queue’s name.
     */
    public void sendTo(String queue, ToDo toDo){
        rabbitTemplate.convertAndSend(queue,toDo);
        LOG.info("Producer> message sent");
    }
}
