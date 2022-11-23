package br.com.fiap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Named("bot")
@RegisterForReflection
public class Bot {

    @Inject
    ConsumerTemplate consumerTemplate;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Handler
    public String process() {

        log.info("Received message: {}", consumerTemplate.receiveBody("seda:kafka-messages", 10000, String.class));

        return consumerTemplate.receiveBody("seda:kafka-messages", 10000, String.class);
    }

}
