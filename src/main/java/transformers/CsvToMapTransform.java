package transformers;

import io.vavr.control.Try;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvToMapTransform {

    @Handler
    public Map doHandler(Exchange exchange) {

        List headers = (List) exchange.getProperties().get("csv_headers");
        String line = (String) exchange.getIn().getBody();
        Map data = new LinkedHashMap();

        headers.forEach(o -> {
            String key = String.valueOf(o).toLowerCase();
            key = key.replaceAll("\\r\\n|\\r|\\n|\"", "");
            String value = Try.of(() -> line.split(",")[headers.indexOf(String.valueOf(o).toLowerCase())]).getOrElse("");
            value = value.replaceAll("\\r\\n|\\r|\\n|\"", "");
            data.put(key, value);
        });

        return data;

    }
}
