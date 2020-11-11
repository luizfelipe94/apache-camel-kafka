package transformers;

import com.cdp.oiads.OiadsEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.component.kafka.KafkaConstants;

import java.util.Map;

public class MapToOiadsEventAvroTransform {

    @Handler
    public OiadsEvent doHandler(Exchange exchange) {

        Map data = (Map) exchange.getIn().getBody();

        OiadsEvent oiadsEvent = new OiadsEvent();
        oiadsEvent.setCampaignuuid(String.valueOf(data.get("campaignuuid")));
        oiadsEvent.setEvent(String.valueOf(data.get("event")));
        oiadsEvent.setZoneuuid(String.valueOf(data.get("zoneuuid")));
        oiadsEvent.setEventdate(String.valueOf(data.get("eventdate")));

        exchange.getIn().setHeader(KafkaConstants.KEY, oiadsEvent.getCampaignuuid());
//        exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY, null);
//        exchange.getIn().setHeader(KafkaConstants.TOPIC, "OIADS_EVENTS");

        return oiadsEvent;

    }

}
