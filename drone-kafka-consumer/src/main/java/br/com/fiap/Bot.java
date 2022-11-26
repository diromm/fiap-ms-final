package br.com.fiap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.camel.ConsumerTemplate;

@ApplicationScoped
@Path("example")
public class Bot {

    @Inject
    ConsumerTemplate consumerTemplate;

    @GET
    public String process() {
        return consumerTemplate.receiveBody("seda:kafka-messages", 10000, String.class);
    }
}
