package routes;

import org.apache.camel.builder.RouteBuilder;
import processors.HeaderTemplate;


public class FileLoaderRoute extends RouteBuilder {

    public void configure() throws Exception {

        onException(Exception.class)
                .handled(true)
                .log("Exception While inserting messages.").process(new ExceptionProcessor());

        from("timer://startLoad?repeatCount=1&delay=3000")
                .routeId("fileLoaderStartRoute")
                .process(new HeaderTemplate())
                .pollEnrich(this.getFileUri())
                .streamCaching()
                .to("direct:fileReaderRoute");

    }

    private String getFileUri(){
        return "file:data/input?fileName=events.csv&noop=true";
    }
}
