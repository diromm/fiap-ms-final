package br.com.fiap;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

import br.com.fiap.schema.DroneSchema;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

@ApplicationScoped
public class Routes extends RouteBuilder {

    // Cabeçalho da mensagem do Debezium responsável por definir qual operação foi
    // capturada
    // c = insert
    // u = update
    // d = delete
    private static final String HEADER_OPERATION = "OP";

    // Cabeçalho que contem a KEY do par KEY+VALUE que vai para o Infinispan

    private static final String HEADER_KEY = "id_drone";

    // Tipos de operações capturadas e geradas pelo Debezium
    private static final String DEBEZIUM_INSERT = "c";
    private static final String DEBEZIUM_UPDATE = "u";
    private static final String DEBEZIUM_DELETE = "d";

    @Override
    public void configure() throws Exception {

        // Predicates usados para roteamento
        final Predicate isInsertOperation = header(HEADER_OPERATION).isEqualTo(DEBEZIUM_INSERT);
        final Predicate isUpdatetOperation = header(HEADER_OPERATION).isEqualTo(DEBEZIUM_UPDATE);
        final Predicate isDeleteOperation = header(HEADER_OPERATION).isEqualTo(DEBEZIUM_DELETE);

        // kafka consumer
        final DroneSchema dc = new DroneSchema();

        from("kafka:{{kafka.topic.name}}")
                .routeId("FromKafka2Seda")
                .setBody(jsonpath("$.payload"))
                .setHeader(HEADER_KEY, jsonpath("after.id_drone"))
                .log(LoggingLevel.WARN, "${body}")
                .setBody(jsonpath("after"))            
                .log(LoggingLevel.WARN, "${body}")
                .marshal().json()
                .unmarshal(new JacksonDataFormat(dc.getClass()))
                .log(LoggingLevel.WARN, "${body}")
                .to("telegram:bots?chatId=155463659");
            

    }
}
