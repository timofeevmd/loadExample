package ExamplePerf.Load;

import Healper.ExampleDataParams;
import Healper.Listener;
import Requests.ExampleHttpRequest;
import Requests.Login;
import Requests.Variables;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class ExampleLoad extends Variables {

    Listener listener = new Listener();
    Login login = new Login();
    ExampleHttpRequest exampleHttpRequest = new ExampleHttpRequest();

    @Test(dataProvider = "paramsExample", dataProviderClass = ExampleDataParams.class)
    public void exampleLoad(int maxThreads, double rps, int rumpUp, int step, int durationStep) throws IOException, ParseException {
        TestPlanStats stats = testPlan(
                csvDataSet(CSVPOOL)
                        .variableNames("Login", "Password")
                        .ignoreFirstLine()
                        .delimiter(","),
                rpsThreadGroup("example load test")
                        .maxThreads(maxThreads)
                        .rampToAndHold((rps * (step / 100.0)) * (maxThreads * (step / 100.0)), Duration.ofSeconds(rumpUp), Duration.ofSeconds(durationStep))
                        .rampToAndHold((rps * ((step * 2)/ 100.0)) * (maxThreads * (step/ 100.0)), Duration.ofSeconds(rumpUp), Duration.ofSeconds(durationStep))
                        .rampToAndHold((rps * ((step * 3)/ 100.0)) * (maxThreads * (step/ 100.0)), Duration.ofSeconds(rumpUp), Duration.ofSeconds(durationStep))
                        .rampToAndHold((rps * ((step * 4)/ 100.0)) * (maxThreads * (step/ 100.0)), Duration.ofSeconds(rumpUp), Duration.ofSeconds(durationStep))
                        .rampToAndHold((rps * ((step * 5)/ 100.0)) * (maxThreads * (step/ 100.0)), Duration.ofSeconds(rumpUp), Duration.ofSeconds(durationStep))
                        .children(
                            onceOnlyController(
                                    login.login("${Login}", "${Password}")
                                            .children(
                                                    jsr223PostProcessor("get grafana cookie"
                                                            ,"if (prev.responseCode == '503'){"
                                                                    + "prev.successful = true;"
                                                                    + "};"
                                                                    + "def headers = prev.getResponseHeaders();"
                                                                    + "def header = headers =~ /Set-Cookie:\\s(.+?)[$;]/;"
                                                                    + "vars.put(\"grafanaSession\",header[0][1]);")
                                            )
                            ),
                            exampleHttpRequest.exampleHttpSampler("${grafanaSession}", "src/main/resources/exampleJson.json")
                                    .children(
                                            jsr223PostProcessor("change controller response code"
                                    , "if (prev.responseCode == '503' || prev.responseCode == '404'){"
                                                    + "prev.successful = true;"
                                                    + "}"
                                            )
                                    ),
                            exampleHttpRequest.exampleDummySampler("src/main/resources/exampleJson.json"
                                    , "http://example.com"
                                    , "200"
                                    , "Ok"
                                    , 1
                                    , 5)
                        )
                ,resultsTreeVisualizer()
                ,listener.getJtlWriter("testName")
                ,listener.getHtmlReporter("htmlReporterName")
                ,listener.getInfluxDbBackendListener(10000
                        , "example"
                        , "jmeter"
                        , ".*"
                        , "example/load/")
        )
        //.sequentialThreadGroups()
        .run();
        //assertThat(stats.overall().sampleTime().perc95()).isLessThan(Duration.ofSeconds((long)5));
    }

}
