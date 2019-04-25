package UTILLS.Tools.utils;

/**
 * 多个值包装器，通常再一个函数中要返回多个值(最多4个)时使用,超过忽略。 下标从0开始
 * 
 * @author PanJun
 *
 */
public class JValues {

    private Object[] _values = new Object[4];

    public JValues() {
    }

    public JValues(Object... values) {
        int len = Math.min(values.length, _values.length);
        for (int i = 0; i < len; i++) {
            _values[i] = values[i];
        }
    }

    public Object get(int i) {
        if (i > -1 && i < _values.length) {
            return _values[i];
        }
        return null;
    }

    public void set(int i, Object v) {
        if (i > -1 && i < _values.length) {
            _values[i] = v;
        }
    }

    public Integer asInt(int i) {
        return STR.toInteger(get(i));
    }

    public Long asLong(int i) {
        return STR.toLong(get(i));
    }

    public String asString(int i) {
        return STR.toString(get(i));
    }

    public Double asDouble(int i) {
        return STR.toDouble(get(i));
    }

}
