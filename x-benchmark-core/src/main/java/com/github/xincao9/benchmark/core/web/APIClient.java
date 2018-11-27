package com.github.xincao9.benchmark.core.web;

import com.github.xincao9.benchmark.core.interfaces.Result;
import com.github.xincao9.benchmark.core.util.Logger;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author 510655387@qq.com
 */
public class APIClient implements Result {

    private String host = "localhost";
    private int port = 80;
    private String username = "admin";
    private String passwd = "admin";
    private final String topic;
    private final String title;

    public APIClient(String host, int port, String username, String passwd, String topic, String title) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.passwd = passwd;
        this.topic = topic;
        this.title = title;
    }

    @Override
    public void output(int concurrency, int total, int messageSize, long minRT, long maxRT, double averageRT, double tps, double errorNumber) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URI uri = new URIBuilder().setScheme("http").setHost(host + ":" + port).setPath("/professor_x_web/do_login").build();
            HttpPost post = new HttpPost(uri);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("account", username));
            nvps.add(new BasicNameValuePair("passwd", passwd));
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(nvps);
            post.setEntity(form);
            CloseableHttpResponse response = client.execute(post);
            boolean ok = false;
            for (Header header : response.getAllHeaders()) {
                if (header.getName().equalsIgnoreCase("Location")) {
                    if (header.getValue().contains("list")) {
                        ok = true;
                    }
                    break;
                }
            }
            if (ok) {
                Logger.info("登陆成功");
            } else {
                Logger.info("登陆失败");
                return;
            }
            uri = new URIBuilder().setScheme("http").setHost(host + ":" + port).setPath("/professor_x_web/report/do_add_data").build();
            post = new HttpPost(uri);
            Data data = new Data();
            data.setTitle(title);
            data.setTopic(topic);
            data.setSampleSize(total);
            data.setConcurrency(concurrency);
            data.setMinRt((int) minRT);
            data.setMaxRt((int) maxRT);
            data.setAverageRt(averageRT);
            data.setTps(tps);
            data.setErrorRate(errorNumber);
            ObjectMapper objectMapper = new ObjectMapper();
            String c = objectMapper.writeValueAsString(data);
            GzipCompressingEntity gce = new GzipCompressingEntity(new StringEntity(c, "UTF-8"));
            post.setEntity(gce);
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            Logger.info(EntityUtils.toString(entity));
        } catch (URISyntaxException e) {
            Logger.info(e.getMessage());
        } catch (IOException e) {
            Logger.info(e.getMessage());
        } catch (ParseException e) {
            Logger.info(e.getMessage());
        }

    }
}
