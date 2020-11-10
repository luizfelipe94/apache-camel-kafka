package transformers;

import com.cdp.oiads.OiadsEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;

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

        return oiadsEvent;

    }

}
