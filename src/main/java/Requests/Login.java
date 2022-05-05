package Requests;

import org.apache.http.entity.ContentType;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import java.nio.charset.StandardCharsets;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;

public class Login extends Variables {

    public DslHttpSampler login(String user, String password){
        return httpSampler(" POST Grafana logIn","http://" + "127.0.0.1" + ":3000" + "/login")
                .protocol("http")
                .method(HTTPConstants.POST)
                .encoding(StandardCharsets.UTF_8)
                .followRedirects(true)
                .contentType(ContentType.APPLICATION_JSON)
                .header("accept", ACCEPT)
                .header("Accept-Encoding", ACCEPTENCODING)
                .header("Accept-Language", ACCEPTLANGUAGE)
                .header("Connection", CONNECTION)
                .header("Content-Length", CONTENTLENGTH)
                .header("content-type", CONTENTTYPE)
                .header("Cookie", COOKIE)
                .header("Host", HOST)
                .header("Origin", ORIGIN)
                .header("Referer", REFERER)
                .header("sec-ch-ua", SECCHUA)
                .header("sec-ch-ua-mobile", SECCHUAMOBILE)
                .header("sec-ch-ua-platform", SECCHUAPLATFORM)
                .header("Sec-Fetch-Dest", SECFEATCHDEST)
                .header("Sec-Fetch-Mode", SECFEATCMODE)
                .header("Sec-Fetch-Site", SECFEATCSITE)
                .header("User-Agent", USERAGENT)
                .body("{"
                    + "\"user\":\"" + user + "\","
                    + "\"password\":\"" + password + "\""
                    + "}"
                );
    }
}
