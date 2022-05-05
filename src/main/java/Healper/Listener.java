package Healper;

import us.abstracta.jmeter.javadsl.core.listeners.HtmlReporter;
import us.abstracta.jmeter.javadsl.core.listeners.InfluxDbBackendListener;
import us.abstracta.jmeter.javadsl.core.listeners.JtlWriter;

import java.io.IOException;
import java.time.Instant;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class Listener {
    protected final String JTLWRITERCSVLOGS = "logs/csv/";
    protected final String HTMLREPORTER = "logs/html/";

    protected final String DBHOST = "http://127.0.0.1";
    protected final int DBPORT = 8086;
    protected final String DBNAME = "loadTestingDB";

    public JtlWriter getJtlWriter(String testName){
        return jtlWriter(JTLWRITERCSVLOGS + testName + Instant.now().toString().replace(":", "-") + ".csv");
    }

    public HtmlReporter getHtmlReporter(String htmlReportName) throws IOException {
        return htmlReporter(HTMLREPORTER + htmlReportName + Instant.now().toString().replace(":", "-"));
    }

    public InfluxDbBackendListener getInfluxDbBackendListener(int testQueueSize, String testApplication, String testMeasurement, String testSamplersRegex, String testTitle){
        return influxDbListener(DBHOST + ":" + DBPORT + "/write?db=" + DBNAME)
                .queueSize(testQueueSize)
                .application(testApplication)
                .measurement(testMeasurement)
                .samplersRegex(testSamplersRegex)
                .title(testTitle);
    }
}
