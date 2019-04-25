package UTILLS.Tools.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类，可视为JDK Collections兄弟类, CLLT == collection
 * 
 * @author PanJun
 * 
 */
public final class CLLT {

    public static <T> boolean isEmpty(Collection<T> coll) {
        return (coll == null) || coll.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return (map == null) || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] array) {
        return (array == null) || array.length == 0;
    }

    /**
     * 把array里的内容加入到coll中
     * 
     * @param <T>
     * @param coll
     * @param array
     * @return coll
     */
    public static <T> Collection<T> addAll(Collection<T> coll, T[] array) {
        if (array != null || coll != null) {
            for (T a : array) {
                coll.add(a);
            }
        }
        return coll;
    }

    /**
     * 把collection里的内容连接成字符串
     * 
     * @param <T>
     * @param coll
     * @param seperator
     *            分割符
     * @param quoter
     *            引号
     * @return
     */
    public static <T> String link(Collection<T> coll, String seperator, String quoter) {
        if (coll == null || coll.isEmpty())
            return "";

        seperator = seperator == null ? "" : seperator;
        quoter = quoter == null ? "" : quoter;

        String loopSep = "";
        StringBuilder sb = new StringBuilder();
        for (T t : coll) {
            sb.append(loopSep).append(quoter).append(t).append(quoter);
            loopSep = seperator;
        }
        return sb.toString();
    }

    /**
     * 把collection里的内容连接成字符串
     * 
     * @param <T>
     * @param coll
     * @param seperator
     * @return
     */
    public static <T> String link(Collection<T> coll, String seperator) {
        return link(coll, seperator, "");
    }

    /**
     * 把Array里的内容连接成字符串
     * 
     * @param <T>
     * @param array
     * @param seperator
     *            分割符
     * @param quoter
     *            引号
     * @return
     */
    public static <T> String link(T[] array, String seperator, String quoter) {
        if (array == null || array.length == 0)
            return "";

        seperator = seperator == null ? "" : seperator;
        quoter = quoter == null ? "" : quoter;

        String loopSep = "";
        StringBuilder sb = new StringBuilder();
        for (T t : array) {
            sb.append(loopSep).append(quoter).append(t).append(quoter);
            loopSep = seperator;
        }
        return sb.toString();
    }

    /**
     * 把Array里的内容连接成字符串
     * 
     * @param <T>
     * @param array
     * @param seperator
     * @return
     */
    public static <T> String link(T[] array, String seperator) {
        return link(array, seperator, "");
    }

    /**
     * 把Array里的内容连接成字符串
     * 
     * @param <T>
     * @param array
     * @return
     */
    public static <T> String link(T[] array) {
        return link(array, ",", "");
    }

    /**
     * 把collection里的对象转移到指定类型的array返回
     * 
     * @param <T>
     * @param coll
     * @param resultClass
     *            数组类型
     * @return
     */
    public static <T> T[] toArray(Collection<T> coll, Class<T> resultClass) {
        if (coll == null)
            return null;

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(resultClass, coll.size());

        int i = 0;
        for (Iterator<T> iter = coll.iterator(); iter.hasNext(); i++) {
            result[i] = iter.next();
        }
        return result;
    }

    /**
     * 把collection里的对象拷贝成新对象再转移到指定类型的array返回
     * 
     * @param <T>
     * @param <C>
     * @param coll
     * @param resultClass
     *            数组类型，此类型要有缺省构造函数
     * @return
     */
    public static <T, C> T[] toArrayWithNew(Collection<C> coll, Class<T> resultClass) {
        if (coll == null)
            return null;

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(resultClass, coll.size());
        int i = 0;
        for (Iterator<C> iter = coll.iterator(); iter.hasNext(); i++) {
            try {
                result[i] = BEAN.copyProp(iter.next(), resultClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    /**
     * 从Map中获取值,jQuery风格
     * 
     * @param map
     * @param k
     * @return
     */
    public static <K, V> V val(Map<K, V> map, K k) {
        if (map == null) {
            return null;
        } else {
            return map.get(k);
        }
    }

    /**
     * 从Map中获取值，如果没有使用缺省值返回
     * 
     * @param map
     * @param k
     * @return
     */
    public static <K, V> V val(Map<K, V> map, K k, V defaultValue) {
        if (map == null) {
            return null;
        } else {
            V ret = map.get(k);
            if (ret == null) {
                ret = defaultValue;
            }
            return ret;
        }
    }

    /**
     * 取列表中第一个元素
     * 
     * @param list
     * @return
     */
    public static <T> T first(List<T> list) {
        return val(list, 0);
    }

    /**
     * 取列表中最后元素
     * 
     * @param list
     * @return
     */
    public static <T> T last(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }

    /**
     * 取列表中下标元素
     * 
     * @param list
     * @param index
     * @return
     */
    public static <T> T val(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return null;
        } else {
            return list.get(index);
        }
    }

    /**
     * 取列表中下标元素，并转换成指定类型，无法转换，返回空值
     * 
     * @param list
     * @param index
     * @return
     */
    @SuppressWarnings("all")
    public static <T> T val(List list, int index, Class<T> clazz) {
        if (list == null || index < 0 || index >= list.size()) {
            return null;
        } else {
            Object ret = list.get(index);
            if (clazz.isInstance(ret)) {
                return (T) ret;
            } else {
                return null;
            }
        }
    }

    /**
     * 从Map中获取元素
     * 
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V get(Map<K, V> map, K key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 遍历迭代source，并逐个项调用methodName方法，然后把方法返回值放到target中
     * 
     * @param source
     * @param methodName
     * @param target
     * @return
     */
    public static <K, V> Map<K, V> toMap(Collection<V> source, Function<V, K> keyExtractor) {
        Map<K, V> ret = new HashMap<>();
        if (source == null || source.isEmpty() || keyExtractor == null) {
            return ret;
        }

        for (V v : source) {
            K k = keyExtractor.apply(v);
            ret.put(k, v);
        }

        return ret;
    }

    /**
     * 依次在source每项上调用predicate，存在方法返回值与给定值相等情况，返回下标，<br>
     * 否则返回-1
     * 
     * @param source
     * @param filterMethod
     * @param value
     * @return
     */
    public static <T> int indexOf(List<T> source, Predicate<T> predicate) {
        if (source == null || predicate == null) {
            return -1;
        }

        int i = 0;
        for (T o : source) {
            if (predicate.test(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 过滤source上的所有数据，并放置到一个新的ArrayList返回
     * 
     * @param source
     * @param predicate
     * @return 总返回非Null的列表
     */
    public static <T> List<T> filter(Collection<T> source, Predicate<T> predicate) {
        List<T> ret = new ArrayList<T>();
        if (source == null) {
            return ret;
        }

        for (T o : source) {
            if (predicate.test(o)) {
                ret.add(o);
            }
        }
        return ret;
    }

    /**
     * 依次在source每项上调用某个方法，如果返回值与给定值相等，把这一项加到一个列表中返回
     * 
     * @param source
     * @param filterMethod
     * @param value
     * @return 总返回非Null的列表
     */
    @SuppressWarnings("all")
    public static <T> List<T> filter(Collection source, String filterMethod, Object value) {
        List ret = new ArrayList();
        if (source == null) {
            return ret;
        }

        try {
            Method method = null;
            for (Object o : source) {
                if (method == null) {
                    method = o.getClass().getMethod(filterMethod);
                    method.setAccessible(true);
                }
                Object itemVal = method.invoke(o);
                if (BEAN.equals(value, itemVal)) {
                    ret.add(o);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return ret;
    }

    public static <T, R> List<R> map(Collection<T> collection, Function<T, ? extends R> mapper) {
        List<R> ret = new ArrayList<>();
        map(collection, mapper, ret);
        return ret;
    }

    public static <T, R> void map(Collection<T> collection, Function<T, ? extends R> mapper, Collection<R> target) {
        if (null == collection || collection.size() == 0 || mapper == null || target == null) {
            return;
        }

        for (T t : collection) {
            target.add(mapper.apply(t));
        }
    }

    /**
     * 把keyVals按键值的顺序放入一个map返回
     * 
     * @param keyVals
     * @return
     */
    @SuppressWarnings("all")
    public static <K, V> Map<K, V> asMap(Object... keyVals) {
        Map ret = new HashMap();
        for (int i = 0; i + 1 < keyVals.length; i += 2) {
            ret.put(keyVals[i], keyVals[i + 1]);
        }
        return ret;
    }

    /**
     * 简化泛形Map构造
     * 
     * @return
     */
    public static <K, V> Map<K, V> HashMap() {
        return new HashMap<K, V>();
    }

    /**
     * 简化泛形List构造
     * 
     * @return
     */
    public static <V> List<V> ArrayList() {
        return new ArrayList<V>();
    }

    /**
     * 简化泛形List构造
     * 
     * @return
     */
    public static <V> List<V> LinkedList() {
        return new LinkedList<V>();
    }

    /**
     * 简化泛形Set构造
     * 
     * @return
     */
    public static <V> Set<V> HashSet() {
        return new HashSet<V>();
    }

    /**
     * 把集合转成数组
     * 
     * @param collection
     * @param clazz
     * @return
     */
    public static <T> T[] asArray(Collection<T> collection, Class<T> clazz) {
        if (collection == null) {
            return null;
        }

        @SuppressWarnings("all")
        T[] ret = (T[]) Array.newInstance(clazz, collection.size());
        int i = 0;
        for (T t : collection) {
            ret[i] = t;
        }
        return ret;
    }

    /**
     * 在集合中查找一个满足条件的任意项
     * 
     * @param items
     * @param condition
     * @return
     */
    public static <T> T any(Collection<T> items, Predicate<T> condition) {
        if (items == null || condition == null || items.isEmpty()) {
            return null;
        }

        for (T t : items) {
            if (condition.test(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 在数组中查找一个满足条件的任意项
     * 
     * @param items
     * @param condition
     * @return
     */
    public static <T> T any(T[] items, Predicate<T> condition) {
        if (items == null || condition == null || items.length == 0) {
            return null;
        }

        for (T t : items) {
            if (condition.test(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 在集合中包含满足条件的元素
     * 
     * @param items
     * @param condition
     * @return
     */
    public static <T> boolean has(Collection<T> items, Predicate<T> condition) {
        if (items != null && condition != null) {
            for (T t : items) {
                if (condition.test(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在数组中包含满足条件的元素
     * 
     * @param items
     * @param condition
     * @return
     */
    public static <T> boolean has(T[] items, Predicate<T> condition) {
        if (items != null && condition != null) {
            for (T t : items) {
                if (condition.test(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在集合中查找一个满足条件的任意项
     * 
     * @param items
     * @param condition
     * @return
     */
    public static <T> T any(Collection<T> items, Predicate<T> condition, T orElse) {
        if (items == null || condition == null || items.isEmpty()) {
            return orElse;
        }

        return items.stream().filter(condition).findFirst().orElse(orElse);
    }

}