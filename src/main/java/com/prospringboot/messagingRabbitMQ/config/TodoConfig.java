package com.prospringboot.messagingRabbitMQ.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoConfig {

    /*
        SimpleRabbitListenerContainerFactory. This factory is required
        when using the @RabbitListener annotation for custom setup
        because you are working with ToDo instances; it is necessary set the
        message converter.

        Jackson2JsonMessageConverter. This converter is used for
        producing (with the RabbitTemplate) and for consuming
        (@RabbitListener); it uses the Jackson libraries for doing its
        mapping and conversion.
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory (ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory listenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        return listenerContainerFactory;
    }

    /*
        RabbitTemplate. This a helper class that can send and receive
        messages. In this case, it is necessary to customize it to produce JSON
        objects using the Jackson converter.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /*
        Queue. You can manually create a queue, but in this case, you are
        creating it programmatically. You pass the name of the queue, if is
        going to be durable or exclusive, and auto-delete.

        There are two types of Queue - durable and non-durable.
        Durable queue survives a server restart.
     */
    @Bean
    public Queue createQueue(@Value("${todo.amqp.queue}") String queue){
        return new Queue(queue, true, false, false);
    }

    /*
        Remember that in the AMQP protocol, you need an exchange that is bound to a
        queue, so this particular example creates at runtime a queue named spring-boot, and
        by default, all the queues are bound to a default exchange. That’s why you didn’t provide
        any information about an exchange. So, when the producer sends the message, it is sent
        first to the default exchange and then routed to the queue (spring-boot).
     */
}
