package UTILLS.Tools.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java Bean工具类
 * 
 * @author PanJun
 * 
 */
public final class BEAN {

    /**
     * 调用equals方法判断o1, o2是否相等
     * 
     * @param o1
     * @param o2
     * @return
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2)
            return true;

        if (o1 != null)
            return o1.equals(o2);

        return o2 == null;
    }

    /**
     * 判断o是否与values里任何一个相等
     * 
     * @param o
     * @param values
     * @return
     */
    public static boolean equalsAny(Object o, Object... values) {
        for (Object value : values) {
            if (equals(o, value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 把原对象的属性值拷贝到目标对象，并返回目标对象；<b>拷贝空值</b>
     * 
     * @param <T>
     * @param source
     * @param target
     * @return 目标对象
     */
    public static <T> T copyProp(Object source, T target) {
        return copyProp(source, target, false);
    }

    private static boolean isZteClass(Type type) {
        if (!(type instanceof Class))
            return false;
        String clzzName = ((Class<?>) type).getName();
        return clzzName.startsWith("com.zte");
    }

    private static Map<?, ?> copyMap(Map<?, ?> source, Type keyType, Type valType, boolean copyNull) throws Exception {
        if (!(keyType instanceof Class))
            throw new UnsupportedOperationException("makeTargetMap " + keyType);

        Class<?> keyClzz = (Class<?>) keyType;

        Map<Object, Object> result = new HashMap<Object, Object>();
        for (Object k : source.keySet()) {
            Object srcVal = source.get(k);
            Object value = srcVal;
            Object key = k;
            if (isZteClass(keyClzz))
                key = copyProp(k, keyClzz.newInstance(), copyNull);

            if (isZteClass(valType)) {
                value = copyProp(srcVal, ((Class<?>) valType).newInstance(), copyNull);
            } else if (checkCopyAsList(srcVal, valType)) {
                Type actualType = ((ParameterizedType) valType).getActualTypeArguments()[0];
                value = copyList((List<?>) srcVal, (Class<?>) actualType, copyNull);
            } else if (checkCopyAsMap(srcVal, valType)) {
                ParameterizedType prmType = (ParameterizedType) valType;
                Type subKeyType = prmType.getActualTypeArguments()[0];
                Type subValType = prmType.getActualTypeArguments()[1];
                value = copyMap((Map<?, ?>) srcVal, subKeyType, subValType, copyNull);
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把原对象的属性值拷贝到目标对象，并返回目标对象；不处理复合属性，可控制是否拷贝空值
     * 
     * @param <T>
     * @param source
     * @param target
     * @param copyNull
     *            是否拷贝空值
     * @return 目标对象
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T copyProp(Object source, T target, boolean copyNull) {
        if (source == null || target == null)
            return target;

        Map<String, Method> getterMap = new HashMap<String, Method>();
        for (Method m : source.getClass().getMethods()) {
            if (m.getParameterTypes().length > 0)
                continue;

            String name = m.getName();
            if (name.startsWith("get") && name.length() > 3) {
                name = name.substring(3);
                getterMap.put(name, m);
            } else if (name.startsWith("is") && name.length() > 2 && m.getReturnType() == boolean.class) {
                name = name.substring(2);
                getterMap.put(name, m);
            }

        }

        for (Method setter : target.getClass().getMethods()) {
            String name = setter.getName();
            Type[] paramTypes = setter.getGenericParameterTypes();
            if (name.startsWith("set") && name.length() > 3 && paramTypes.length == 1) {
                name = name.substring(3);
                Method getter = getterMap.get(name);
                if (getter != null) {
                    try {
                        Object value = getter.invoke(source);
                        if (value != null) {
                            Type paramType = paramTypes[0];
                            if (value instanceof Enum && Enum.class.isAssignableFrom((Class<?>)paramType)) {
                                try {
                                    value = Enum.valueOf((Class<Enum>)paramType, ((Enum<?>)value).name());
                                } catch (Exception e) {
                                }
                            } else if (isZteClass(paramType)) {
                                try {
                                    value = copyProp(value, ((Class<?>) paramType).newInstance(), copyNull);
                                } catch (InstantiationException e) {
                                }
                            } else if (checkCopyAsList(value, paramType)) {
                                Type actualType = ((ParameterizedType) paramType).getActualTypeArguments()[0];
                                value = copyList((List<?>) value, (Class<?>) actualType, copyNull);
                            } else if (checkCopyAsMap(value, paramType)) {
                                Type keyType = ((ParameterizedType) paramType).getActualTypeArguments()[0];
                                Type valType = ((ParameterizedType) paramType).getActualTypeArguments()[1];
                                try {
                                    value = copyMap((Map<?, ?>) value, keyType, valType, copyNull);
                                } catch (Exception e) {
                                    value = null;
                                }
                            }

                            setter.invoke(target, value);
                        } else if (copyNull) {
                            setter.invoke(target, value);
                        }
                    } catch (IllegalArgumentException e) {
                        // do nothing
                    } catch (IllegalAccessException e) {
                        // do nothing
                    } catch (InvocationTargetException e) {
                        // do nothing
                    }
                }

            }
        }

        return target;
    }

    public static <T> T copyAs(Object srcBean, Class<T> targetClass) {
        if (srcBean == null) {
            return null;
        }

        T ret;
        try {
            ret = targetClass.newInstance();
        } catch (Exception e) {
            return null;
        }
        return copyProp(srcBean, ret);
    }

    /**
     * 判断Value是否是List类型，type是泛型List，从而他们可以作为List进行bean Copy
     * 
     * @param value
     * @param type
     * @return
     */
    private static boolean checkCopyAsList(Object value, Type type) {
        if (!(value instanceof List) || !(type instanceof ParameterizedType))
            return false;

        ParameterizedType paramType = (ParameterizedType) type;
        if (!(paramType.getRawType() instanceof Class))
            return false;

        Class<?> rawType = (Class<?>) paramType.getRawType();
        if (!List.class.isAssignableFrom(rawType) || paramType.getActualTypeArguments().length != 1)
            return false;

        return true;
    }

    /**
     * 判断Value是否是Map类型，type是泛型Map，从而他们可以作为Map进行bean Copy
     * 
     * @param value
     * @param type
     * @return
     */
    private static boolean checkCopyAsMap(Object value, Type type) {
        if (!(value instanceof Map) || !(type instanceof ParameterizedType))
            return false;

        ParameterizedType paramType = (ParameterizedType) type;
        if (!(paramType.getRawType() instanceof Class))
            return false;

        Class<?> rawType = (Class<?>) paramType.getRawType();
        if (!Map.class.isAssignableFrom(rawType) || paramType.getActualTypeArguments().length != 2)
            return false;

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClzz, boolean copyNull) {
        if (sourceList == null || targetClzz == null)
            return null;

        List<T> ret = new ArrayList<T>();
        for (Object source : sourceList) {
            if (isZteClass(targetClzz)) {
                try {
                    T target = targetClzz.newInstance();
                    ret.add(copyProp(source, target, copyNull));
                } catch (Exception e) {
                    // do nothing
                }
            } else if (targetClzz.isInstance(source)) {
                ret.add((T) source);
            }
        }
        return ret;
    }

    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClzz) {
        return copyList(sourceList, targetClzz, false);
    }

}