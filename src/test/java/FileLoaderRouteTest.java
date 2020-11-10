import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import routes.FileLoaderRoute;

import java.util.ArrayList;

public class FileLoaderRouteTest extends CamelTestSupport {

    @Override
    public CamelContext createCamelContext() {
        SimpleRegistry registry = new SimpleRegistry();
        CamelContext context = new DefaultCamelContext(registry);
        return context;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileLoaderRoute();
    }

    @Test
    public void FileLoaderRouteTest(){
        ArrayList responseList = (ArrayList) consumer.receiveBody("direct:output");
        System.out.println("responseList : " + responseList.size());
        assertNotEquals(0,responseList.size());

    }

}
