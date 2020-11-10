package routes;

import aggregators.ArrayListAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import transformers.CsvToMapTransform;
import transformers.MapToOiadsEventAvroTransform;

import java.util.concurrent.ExecutorService;

public class FileReaderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        ThreadPoolBuilder builder = new ThreadPoolBuilder(getContext());
        ExecutorService threadPool = builder.poolSize(10).maxPoolSize(10).maxQueueSize(10).build("fileReaderThreadPool");

        from("direct:fileReaderRoute")
                .setProperty(Exchange.CHARSET_NAME, constant("ISO-8859-1"))
                .split().tokenize("\n", 1, true).streaming()
                .executorService(threadPool)
                .bean(new CsvToMapTransform())
                .bean(new MapToOiadsEventAvroTransform())
//                sem agregacao ainda. vai ser testado chamando direto os brokers do kafka. dps testar passando array para o rest proxy
                .aggregate(constant("kafkamsg"), new ArrayListAggregationStrategy())
                .parallelProcessing()
                .executorService(threadPool)
                .completionSize(constant("10"))
                .completionInterval(3000)
                .forceCompletionOnStop()
                .log(LoggingLevel.INFO, "Enviando lote de tamanho ${body.size} - batchId ${header.CamelCorrelationId}")
                .executorService(threadPool)
                .to("direct:kafkaProducerRoute");

    }

}
