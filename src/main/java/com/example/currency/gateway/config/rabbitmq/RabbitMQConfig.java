package com.example.currency.gateway.config.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${gateway.rabbitmq.exchange}")
    String directExchange;

    @Value("${rates.collector.rabbitmq.queue}")
    String ratesCollectorQueueName;
    @Value("${statistics.collector.rabbitmq.queue}")
    String statisticsCollectorQueueName;

    @Value("${rates.collector.rabbitmq.routingkey}")
    private String ratesCollectorRoutingkey;
    @Value("${statistics.collector.rabbitmq.routingkey}")
    private String statisticsCollectorRoutingkey;

    @Bean
    Queue ratesCollectorQueue() {
        return new Queue(ratesCollectorQueueName, false);
    }
    @Bean
    Queue statisticsCollectorQueue() {
        return new Queue(statisticsCollectorQueueName, false);
    }


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    Binding ratesCollectorBinding(Queue ratesCollectorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(ratesCollectorQueue).to(exchange).with(ratesCollectorRoutingkey);
    }
    @Bean
    Binding statisticsCollectorBinding(Queue statisticsCollectorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(statisticsCollectorQueue).to(exchange).with(statisticsCollectorRoutingkey);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
}
