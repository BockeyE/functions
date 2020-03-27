package AAAAAAPracs.http;

import org.springframework.context.annotation.Bean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author bockey
 */
public class https {



    public String sendGetRequest(String aimUrl) throws IOException {
        URL url = new URL(aimUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("GET"); // 设置请求方式
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            result.append(line + "\n");
        }
        connection.disconnect();
        return result.toString();
    }

    public String sendPostRequest(String aimUrl, String json) throws IOException {
        URL url = new URL(aimUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true); // 设置该连接是可以输出的
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        connection.connect();

        OutputStreamWriter out = new  OutputStreamWriter(connection.getOutputStream());
        out.append(json);
        out.flush();
        out.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

        String line = "0";
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) { // 读取数据
            result.append(line + "\n");
        }
        connection.disconnect();
        return result.toString();
    }
    /**
     * 用于http远程请求
     */
    @Bean
    public RestTemplate tokenRetrieveRestTemplate() {

        RestTemplate restTemplate=new RestTemplate();
        //Response status code 4XX or 5XX to the client.
        restTemplate.setErrorHandler(new ThrowErrorHandler());
        return restTemplate;
    }
}
