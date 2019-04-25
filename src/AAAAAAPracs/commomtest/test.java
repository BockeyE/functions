package AAAAAAPracs.commomtest;


import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class test {
    int[] a = {1, 2, 3, 4, 5};

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
//        AllArrangement AllArrangement = new AllArrangement();
//        AllArrangement.com((Integer o1, Integer o2) -> {
//            {
//                return (Integer) o1 - (Integer) o2;
//            }
//        });

//        System.out.println(AllArrangement.a);

//        int k = 30001;
//        for (int i = 0; i < 10001; i++) {
//            if (k % 2 == 0) {
//                k = k / 2;
//                continue;
//            }
//            if (k % 2 == 1) {
//                k = k * 3 + 1;
//                continue;
//            }
//
//        }
//        System.out.println("k= " + k + " after 10000");
        String name = "1";
        String id = "1";
        String phone = "1";
        String address = "1";
        String email = "1";
        Map map = new HashMap();
        map.put("name", "phone");
        map.put("id", "phone");
        map.put("phone", "phone");
        map.put("address", "phone");
        map.put("email", "phone");
        System.out.println(map);
        entityTest e = new entityTest();

        BeanUtils.populate(e, map);
        System.out.println(e);


        MyMap m2 = new MyMap();
        m2.putThis("1", "2").putThis("2,", "3");


    }

    public int[] com(Comparator<Integer> com) {

        for (int i : a) {
            if (com.compare(i, 0) > 0)
                continue;
        }


        return a;
    }
}
