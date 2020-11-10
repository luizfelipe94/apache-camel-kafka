package routes;

import org.apache.camel.builder.RouteBuilder;
import processors.KafkaProducerMsgs;

public class KafkaProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:kafkaProducerRoute")
                .routeId("kafkaProducerRoute")
                .log("chegou na rota do kafka. ${body}")
                .process(new KafkaProducerMsgs());

    }
}
