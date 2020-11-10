import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import routes.FileLoaderRoute;

public class FileLoaderMockRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() throws Exception {
        return new FileLoaderRoute();
    }

    @Test
    public void sampleRouteTest() throws InterruptedException {

        String expected="Hello";

        MockEndpoint mock = getMockEndpoint("mock:output");
        mock.expectedBodiesReceived(expected);

        template.sendBody("direct:sampleInput","Hello" );

        assertMockEndpointsSatisfied();

    }

}
