package AAAAAAPracs.SafeClassCastMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author bockey
 */
public class Safe {
    private Object disposeMap(Map<String,Object> map){
        if(map.containsKey("error")){
            return ((String)map.get("error"));
        }
        return (map);
    }

    private List<String> disposeList(Object items){
        if(items instanceof Collection) {
            return (List<String>)items;
        }
        /*if(items instanceof Map) {
            Map map = (Map) items;
            return map.entrySet();  //Set
        }*/
        if(items.getClass().isArray()) {
            List<String> coll = new ArrayList();
            int length = Array.getLength(items);
            for(int i=0;i<length;i++) {
                Object value = Array.get(items, i);
                coll.add((String) value);
            }
            return coll;
        }
        return null;
    }

    private double disposeAmount(Object param){
        if(param instanceof Integer){
            int amountInt = (int)param;
            return  1.0*amountInt;
        }
        else if(param instanceof Long){
            long amountInt = (long)param;
            return 1.0*amountInt;
        }
        else if(param instanceof String){
            return Double.parseDouble((String)param);
        }
        else {
            double amountDouble = (double)param;
            return amountDouble;
        }
    }

    private int disposeInt(Object param){
        if(param instanceof Integer){
            return  (int)param;
        }
        else if(param instanceof String){
            return Integer.parseInt((String)param);
        }
        else {
            return 0;
        }
    }
}
