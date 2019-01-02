package hashCollision;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bockey
 */
public class hashCollisions {

    public static void main(String[] args) {


        StringBuilder sb = new StringBuilder();

        try (FileReader fr = new FileReader("src/hashCollision/res.txt");
             BufferedReader bfr = new BufferedReader(fr);
        ) {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("read ended");
        Object parse = JSONUtils.parse(String.valueOf(sb));
        System.out.println(parse);

    }


}
