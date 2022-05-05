package Requests;

import org.apache.http.entity.ContentType;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import us.abstracta.jmeter.javadsl.core.samplers.DslDummySampler;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static us.abstracta.jmeter.javadsl.JmeterDsl.dummySampler;
import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;

public class ExampleHttpRequest extends Variables {


    public DslDummySampler exampleDummySampler(String file, String url, String rCode, String rMessage, long leftBound, long rightBound) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject conectDB = (JSONObject) obj;
        long dummyResponseTime = ThreadLocalRandom.current().nextLong(leftBound, rightBound);

        return dummySampler("exampleDummy", conectDB.toString())
                .url(url)
                .responseCode(rCode)
                .responseMessage(rMessage)
                .responseTime(Duration.ofSeconds(dummyResponseTime));
    }

    public DslHttpSampler exampleHttpSampler(String cookie, String file) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(file));
        JSONObject conectDB = (JSONObject) obj;

        return httpSampler("PUT example request",HTTPPROTOCOL + MAINHOST + EXAMPLEPORT + "/api/example")
                .method(HTTPConstants.PUT)
                .encoding(StandardCharsets.UTF_8)
                .followRedirects(true)
                .contentType(ContentType.APPLICATION_JSON)
                .header("accept", ACCEPT)
                .header("Accept-Encoding", ACCEPTENCODING)
                .header("Accept-Language", ACCEPTLANGUAGE)
                .header("Connection", CONNECTION)
                .header("Content-Length", "355")
                .header("content-type", CONTENTTYPE)
                .header("Cookie", cookie)
                .header("Host", HOST)
                .header("Origin", ORIGIN)
                .header("Referer", INFLUXDBREFERER)
                .header("sec-ch-ua", SECCHUA)
                .header("sec-ch-ua-mobile", SECCHUAMOBILE)
                .header("sec-ch-ua-platform", SECCHUAPLATFORM)
                .header("Sec-Fetch-Dest", SECFEATCHDEST)
                .header("Sec-Fetch-Mode", SECFEATCMODE)
                .header("Sec-Fetch-Site", SECFEATCSITE)
                .header("User-Agent", USERAGENT)
                .header("x-grafana-org-id", XGRAFANAORGID)
                .body(conectDB.toString());
    }

}
