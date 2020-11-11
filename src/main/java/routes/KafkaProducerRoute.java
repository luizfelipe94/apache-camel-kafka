package routes;

import org.apache.camel.builder.RouteBuilder;
import processors.KafkaProducerMsgs;
import transformers.MapToOiadsEventAvroTransform;

public class KafkaProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:kafkaProducerRoute")
                .routeId("kafkaProducerRoute")
                .log("chegou na rota do kafka. ${body}")
                .bean(new MapToOiadsEventAvroTransform())
                .to("kafka:OIADS_EVENTS?brokers=b-2.mcare-cdp-developer.reybhc.c9.kafka.us-east-1.amazonaws.com:9092,b-1.mcare-cdp-developer.reybhc.c9.kafka.us-east-1.amazonaws.com:9092" +
                        "&schemaRegistryURL=internal-artemis-837746407.us-east-1.elb.amazonaws.com:8081" +
                        "&keySerializer=org.apache.kafka.common.serialization.StringSerializer" +
                        "&valueSerializer=io.confluent.kafka.serializers.KafkaAvroSerializer");

    }
}
