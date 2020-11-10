package routes;

import org.apache.camel.builder.RouteBuilder;

public class KafkaProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:kafkaProducerRoute")
                .routeId("kafkaProducerRoute")
                .process(exchange -> {
                    System.out.println("teste");
                })
                .log("chegou na rota do kafka. ${body}");

    }
}
