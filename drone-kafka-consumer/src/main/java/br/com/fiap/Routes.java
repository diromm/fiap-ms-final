package br.com.fiap;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final Bot bot = new Bot();
        // produces messages to kafka
        // from("timer:foo?period={{timer.period}}&delay={{timer.delay}}")
        // .routeId("FromTimer2Kafka")
        // .setBody().simple("Message #${exchangeProperty.CamelTimerCounter}")
        // .to("kafka:{{kafka.topic.name}}")
        // .log("Message correctly sent to the topic! : \"${body}\" ");

        // kafka consumer
        from("kafka:{{kafka.topic.name}}")
                .routeId("FromKafka2Seda")
                .log("Received : \"${body}\"")
                .to("seda:kafka-messages");

        from("telegram:bots")
                .bean(bot, "process")
                .to("telegram:bots");
    }
}
