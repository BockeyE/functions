package ObservationModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author bockey
 */
public abstract class Observable {
    public final ArrayList<Class<?>> obslist = new ArrayList<>();

    public <T> void registerObserver(T ob) {
        if (ob == null) {
            throw new NullPointerException();
        }
        this.registerObserver(ob.getClass());
    }

    public void registerObserver(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException();
        }
        synchronized (obslist) {
            if (!obslist.contains(cls)) {
                obslist.add(cls);
            }
        }
    }

    public <T> void ubRegisterObserver(T ob) {
        if (ob == null) {
            throw new NullPointerException();
        }
        this.ubRegisterObserver(ob.getClass());
    }

    public void ubRegisterObserver(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException();
        }
        synchronized (obslist) {
            Iterator<Class<?>> iter = obslist.iterator();
            while (iter.hasNext()) {
                if (iter.next().getName().equals(cls.getName())) {
                    iter.remove();
                    break;
                }
            }
        }
    }

    public void ubRegisterAll() {
        synchronized (obslist) {
            obslist.clear();
        }
    }

    public int countObservers() {
        synchronized (obslist) {
            return obslist.size();
        }
    }

    public void notifyObservers(Object... obs) {
        for (Class<?> cls : obslist) {
            this.notifyObserver(cls, obs);
        }
    }


    public <T> void notifyObserver(T t, Object... obs) {
        if (t == null) {
            throw new NullPointerException();
        }
        this.notifyObserver(t.getClass(), obs);
    }

    public abstract void notifyObserver(Class<?> cls, Object... obs);

}
