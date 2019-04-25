package UTILLS.Tools.cache;

import UTILLS.Tools.utils.NULL;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;



/**
 * 作用非常有限的缓存，固定时间内缓存有效。典型的使用场景如下:<br>
 * 放置非常短的时间内反复使用了相同条件查询数据库渲染Web页面上
 * 
 * @author jbundle
 *
 */
public class LightCache {

    private static class Item {

        public final long millis;

        public final Object data;

        public Item(long millis, Object data) {
            this.millis = millis;
            this.data = data;
        }

        public boolean ifExpired() {
            return System.currentTimeMillis() > millis;
        }

    }

    private final static ConcurrentHashMap<String, Item> holders = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取数据，如果没有或数据超过指定时间，直接调用supplier生产数据，缓存并返回
     * 
     * @param k
     * @param seconds
     * @param supplier
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String k, int seconds, Supplier<T> supplier) {
        k = NULL.nvl(k).toLowerCase();
        Item ret = holders.get(k);
        if (ret == null || ret.ifExpired()) {
            synchronized (LightCache.class) {
                ret = holders.get(k);
                if (ret != null && !ret.ifExpired()) {
                    return (T) ret.data;
                }

                T t = supplier.get();
                ret = new Item(System.currentTimeMillis() + seconds * 1000, t);
                holders.put(k, ret);
            }
        }
        return (T) ret.data;
    }

    public static void remove(String k) {
        k = NULL.nvl(k).toLowerCase();
        holders.remove(k);
    }

    /**
     * 默认缓存5分钟
     * 
     * @param k
     * @param supplier
     * @return
     */
    public static <T> T get(String k, Supplier<T> supplier) {
        return get(k, 60 * 5, supplier);
    }

}
