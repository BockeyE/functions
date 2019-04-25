package UTILLS.Tools.expr.struct;

/**
 * 堆栈类
 * 
 * @author PanJun
 * 
 */
public class Stack<T> {

    private final static class Entry<E> {

        private final Entry<E> prev;
        private final E v;

        public Entry(Entry<E> prev, E value) {
            this.prev = prev;
            this.v = value;
        }

    }

    private Entry<T> top;

    public final T push(T t) {
        Entry<T> ret = new Entry<T>(top, t);
        top = ret;
        return t;
    }

    public final T pop() {
        T ret = null;
        if (top != null) {
            ret = top.v;
            top = top.prev;
        }
        return ret;
    }

    public final T top() {
        return top == null ? null : top.v;
    }

    public final boolean isEmpty() {
        return top == null;
    }

}
