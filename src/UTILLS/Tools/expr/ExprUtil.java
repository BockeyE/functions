package UTILLS.Tools.expr;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final public class ExprUtil {

    public static char upper(char c) {
        if (c < MAPPER_LEN) {
            return charMapper[c].upper;
        } else {
            return c;
        }
    }

    public static boolean isAlpha(char c) {
        if (c < MAPPER_LEN) {
            return charMapper[c].isAlpha;
        } else {
            return false;
        }
    }

    public static int cmp(double d1, double d2) {
        if (Math.abs(d1 - d2) < 0.0001) {
            return 0;
        } else if (d1 < d2) {
            return -1;
        } else {
            return 1;
        }
    }

    public static int cmp(Date d1, Date d2) {
        if (d1 == d2) {
            return 0;
        } else if (d1 == null) {
            return -1;
        } else if (d2 == null) {
            return 1;
        } else {
            long t1 = d1.getTime(), t2 = d2.getTime();
            if (t1 < t2) {
                return -1;
            } else if (t1 > t2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static int cmp(String s1, String s2) {
        if (s1 == s2) {
            return 0;
        } else if (s1 == null) {
            return -1;
        } else if (s2 == null) {
            return 1;
        } else {
            return s1.compareTo(s2);
        }
    }

    public static Integer cmp(Object o1, Object o2) {
        Date date1 = null;
        Date date2 = null;
        Double num1 = null;
        Double num2 = null;
        String str1 = null;
        String str2 = null;
        Boolean b1 = null;
        Boolean b2 = null;

        if ((num1 = ExprUtil.toNum(o1)) != null && (num2 = ExprUtil.toNum(o2)) != null) {
            double d1 = num1;
            double d2 = num2;
            if (Math.abs(d1 - d2) < 0.0001) {
                return 0;
            } else if (d1 < d2) {
                return -1;
            } else {
                return 1;
            }
        } else if ((date1 = ExprUtil.toDate(o1)) != null && (date2 = ExprUtil.toDate(o2)) != null) {
            return date1.compareTo(date2);
        } else if ((str1 = ExprUtil.toString(o1)) != null && (str2 = ExprUtil.toString(o2)) != null) {
            return ExprUtil.cmp(str1, str2);
        } else if ((b1 = ExprUtil.toBool(o1)) != null && (b2 = ExprUtil.toBool(o2)) != null) {
            return b1.compareTo(b2);
        } else {
            return null;
        }
    }

    public static Double toNum(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        } else {
            return null;
        }
    }

    public static Date toDate(Object o) {
        if (o instanceof Date) {
            return (Date) o;
        } else {
            return null;
        }
    }

    public static Boolean toBool(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else {
            return null;
        }
    }

    public static String toString(Object o) {
        if (o instanceof CharSequence) {
            return o.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取javaBean的属性值
     * 
     * @param o
     * @param prop
     * @return
     */
    public static Object getBeanPropValue(Object o, String prop) {
        if (o instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) o;
            Object ret = map.get(prop);
            if (ret != null) {
                return ret;
            }

            for (Object k : map.keySet()) {
                if (prop.compareToIgnoreCase(k.toString()) == 0) {
                    return map.get(k);
                }
            }
        }

        if (o instanceof List<?>) {
            List<?> list = (List<?>) o;
            try {
                int i = Integer.parseInt(prop);
                return list.get(i);
            } catch (Exception noting) {
            }
        }

        try {
            Set<Method> methods = new HashSet<Method>(Arrays.asList(o.getClass().getDeclaredMethods()));
            methods.addAll(Arrays.asList(o.getClass().getMethods()));
            for (Method m : methods) {
                String name = m.getName();
                if (name.startsWith("get") && m.getParameterTypes().length == 0
                        && !void.class.isInstance(m.getReturnType()) && name.substring(3).equalsIgnoreCase(prop)) {
                    m.setAccessible(true);
                    return m.invoke(o);
                }
            }
        } catch (Exception noting) {
        }
        return null;
    }

    private static class CharMapper {
        boolean isAlpha;
        char upper;
    }

    private final static int MAPPER_LEN = 128;
    private final static CharMapper[] charMapper = new CharMapper[MAPPER_LEN];
    static {
        for (char c = 0; c < MAPPER_LEN; c++) {
            charMapper[c] = new CharMapper();
            charMapper[c].isAlpha = c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a' || c <= '9' && c >= '0' || c == '_';
            charMapper[c].upper = Character.toUpperCase(c);
        }
    }

}
