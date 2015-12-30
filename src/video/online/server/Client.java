package video.online.server;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;

public class Client {

    public static void main(String[] args) throws Exception {
        String src = "C:\\temp\\1.avi";
        String dst = "C:\\temp\\out\\1.avi";
//        String src = "C:\\temp\\1.exe";
//        String dst = "C:\\temp\\out\\1.exe";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1:8080")
                    .setPath("/")
                    .setParameter("fp", src)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
//            HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/");
//            HttpGet httpGet = new HttpGet("http://httpbin.org/get");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                FileUtils.writeByteArrayToFile(new File(dst), EntityUtils.toByteArray(entity));
//                System.out.println(EntityUtils.toString(entity));
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

    }
}
