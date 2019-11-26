package AAAAAAPracs.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeChars(json);
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
}
