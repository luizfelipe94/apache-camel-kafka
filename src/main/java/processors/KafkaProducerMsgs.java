package processors;

import com.cdp.oiads.OiadsEvent;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;

public class KafkaProducerMsgs implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        /**
         * FIXME: Refatorar usando o component de kafka do camel e verificar se dá pra enviar bulk, se não der, tirar a agregação
         */

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        ArrayList msgs = (ArrayList) exchange.getIn().getBody();
        try(KafkaProducer<String, OiadsEvent> producer = new KafkaProducer<String, OiadsEvent>(props)){

            for (int i = 0; i < msgs.size(); i++) {
                OiadsEvent oiadsEvent = (OiadsEvent) msgs.get(i);
                ProducerRecord<String, OiadsEvent> record = new ProducerRecord<>("OIADS_EVENTS", String.valueOf(oiadsEvent.getCampaignuuid()), oiadsEvent);
                producer.send(record);
            }

            producer.flush();

        }catch (final SerializationException e){
            e.printStackTrace();
        }

    }
}
