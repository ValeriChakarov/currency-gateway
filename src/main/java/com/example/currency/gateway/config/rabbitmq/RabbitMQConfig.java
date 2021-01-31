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
    @Value("${margins.collector.rabbitmq.queue}")
    String marginsCollectorQueueName;
    @Value("${request.current.rate.collector.rabbitmq.queue}")
    String requestCurrentRateCollectorQueueName;
    @Value("${request.historical.rate.collector.rabbitmq.queue}")
    String requestHistoricalRateCollectorQueueName;

    @Value("${rates.collector.rabbitmq.routingkey}")
    private String ratesCollectorRoutingkey;
    @Value("${margins.collector.rabbitmq.routingkey}")
    private String marginsCollectorRoutingkey;
    @Value("${request.current.rate.collector.rabbitmq.routingkey}")
    private String requestCurrentRateCollectorRoutingkey;
    @Value("${request.historical.rate.collector.rabbitmq.routingkey}")
    private String requestHistoricalRateCollectorRoutingkey;

    @Bean
    Queue ratesCollectorQueue() {
        return new Queue(ratesCollectorQueueName, false);
    }

    @Bean
    Queue marginsCollectorQueue() {
        return new Queue(marginsCollectorQueueName, false);
    }
    @Bean
    Queue requestCurrentRateCollectorQueue() {
        return new Queue(requestCurrentRateCollectorQueueName, false);
    }
    @Bean
    Queue requestHistoricalRateCollectorQueue() {
        return new Queue(requestHistoricalRateCollectorQueueName, false);
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
    Binding marginsCollectorBinding(Queue marginsCollectorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(marginsCollectorQueue).to(exchange).with(marginsCollectorRoutingkey);
    }
    @Bean
    Binding requestCurrentRateCollectorBinding(Queue requestCurrentRateCollectorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(requestCurrentRateCollectorQueue).to(exchange).with(requestCurrentRateCollectorRoutingkey);
    }
    @Bean
    Binding requestHistoricalRateCollectorBinding(Queue requestHistoricalRateCollectorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(requestHistoricalRateCollectorQueue).to(exchange).with(requestHistoricalRateCollectorRoutingkey);
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
