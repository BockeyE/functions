package UTILLS.Tools.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;

public class JSON {

    public static String toJson(Object o) {
        if (o == null) {
            return "";
        }

        return com.alibaba.fastjson.JSON.toJSONString(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (STR.isBlank(json)) {
            return null;
        }
        return com.alibaba.fastjson.JSON.parseObject(json, clazz);
    }

    public static <T> T fromJson(String json, Class<T> clazz, String dateFormat) {
        if (STR.isBlank(json)) {
            return null;
        }
        ParserConfig config = ParserConfig.getGlobalInstance();
//        if (!STR.isBlank(dateFormat)) {
//            config.setDateFormatPattern(dateFormat);
//        }
        return com.alibaba.fastjson.JSON.parseObject(json, clazz, config, new Feature[0]);
    }

    public static <T> List<T> asList(String json, Class<T> clazz) {
        if (STR.isBlank(json)) {
            return new ArrayList<>();
        }
        try {
            return com.alibaba.fastjson.JSON.parseArray(json, clazz);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
