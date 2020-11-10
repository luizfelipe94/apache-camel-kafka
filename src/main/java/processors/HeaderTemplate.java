package processors;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.LinkedList;


public class HeaderTemplate implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = "events.csv";
        String separator = FileSystems.getDefault().getSeparator();
        File currentFile = new File("data" + separator + "input" + separator + fileName);
        this.assembleFirstLineTemplate(exchange, currentFile);
    }

    private void assembleFirstLineTemplate(Exchange exchange, File currentFile) throws IOException {
        String firstLine = Files.asCharSource(currentFile, Charsets.ISO_8859_1).readFirstLine();
        LinkedList headers = new LinkedList<>();
        Arrays.stream(firstLine.split(",")).forEach(s -> headers.add(String.valueOf(s).toLowerCase()));
        exchange.setProperty("csv_headers", headers);
    }

}
