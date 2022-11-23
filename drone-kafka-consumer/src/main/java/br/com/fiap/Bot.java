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

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method processes incoming messages and return responses.
     *
     * @param  message a message coming from a human user in a chat
     * @return         the reply of the bot. Return null if you don't want to answer
     */
    @Handler
    public String process(String message) {
        if (message == null) {
            return null; // skip non-text messages
        }

        log.info("Received message: {}", message);

        return "Why did you say \"" + message.replace("\"", "-") + "\"?";
    }

}
