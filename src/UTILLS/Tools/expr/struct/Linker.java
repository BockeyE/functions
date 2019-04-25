package UTILLS.Tools.expr.struct;

import java.util.Comparator;

final public class Linker<T> {

    public final static class Entry<E> {

        private final Linker<E> linker;

        private Entry<E> prev;

        private Entry<E> next;

        public final E elem;

        public Entry(Linker<E> linker, E value) {
            this.linker = linker;
            this.elem = value;
        }

        public final void remove() {
            if (prev == null) {
                linker.head = next;
            } else {
                prev.next = next;
            }

            if (next == null) {
                linker.tail = prev;
            } else {
                next.prev = prev;
            }
        }

        public final Entry<E> getPrev() {
            return prev;
        }

        public final Entry<E> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return "E:" + elem;
        }

    }

    private Entry<T> head;

    private Entry<T> tail;

    public final Entry<T> add(T t) {
        Entry<T> ret = new Entry<T>(this, t);
        if (head == null) {
            head = ret;
        }
        if (tail == null) {
            tail = ret;
        } else {
            ret.prev = tail;
            tail.next = ret;
            tail = ret;
        }
        return ret;
    }

    public final Entry<T> addSort(T t, Comparator<T> sorter) {
        Entry<T> ret = new Entry<T>(this, t);
        if (head == null) {
            head = ret;
            tail = ret;
        } else {
            Entry<T> after = null;
            Integer nextCmp = null;
            for (Entry<T> e = head; e != null; e = e.next) {
                int iCmp;
                if (nextCmp != null) {
                    iCmp = nextCmp;
                } else {
                    iCmp = sorter.compare(t, e.elem);
                }
                if (iCmp <= 0) {
                    after = e.prev;
                    break;
                }

                if ((e.next == null || (nextCmp = sorter.compare(t, e.next.elem)) <= 0)) {
                    after = e;
                    break;
                }
            }

            if (after == null) {
                ret.next = head;
                head.prev = ret;
                head = ret;
            } else {
                ret.prev = after;
                ret.next = after.next;
                after.next = ret;
                if (ret.next != null) {
                    ret.next.prev = ret;
                } else {
                    tail = ret;
                }
            }
        }
        return ret;
    }

    public final Entry<T> getHead() {
        return head;
    }

    public final Entry<T> getTail() {
        return tail;
    }

    public final T first() {
        return head.elem;
    }

    public final T second() {
        return head.next.elem;
    }

    public final T third() {
        return head.next.next.elem;
    }

}
