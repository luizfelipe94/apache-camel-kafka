package launch;

import org.apache.camel.main.Main;
import routes.FileLoaderRoute;
import routes.FileReaderRoute;
import routes.KafkaProducerRoute;


public class AppLauncher {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.addRouteBuilder(new FileLoaderRoute());
        main.addRouteBuilder(new FileReaderRoute());
        main.addRouteBuilder(new KafkaProducerRoute());
        main.run();
    }

}
