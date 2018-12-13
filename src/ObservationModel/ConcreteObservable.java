package ObservationModel;

/**
 * @author bockey
 */
public class ConcreteObservable extends Observable {
    public static ConcreteObservable instance = null;

    public static synchronized ConcreteObservable getInstance() {
        if (instance == null) {
            instance = new ConcreteObservable();
        }
        return instance;
    }

    @Override
    public void notifyObservers(Object... obs) {
        for (Class<?> ob : obslist) {
            this.notifyObserver(ob, obs);
        }
    }

    @Override
    public <T> void notifyObserver(T t, Object... obs) {
        if (t == null) {
            throw new NullPointerException();
        }
        this.notifyObserver(t.getClass(), obs);
    }

    @Override
    public void notifyObserver(Class<?> cls, Object... obs) {
        if (cls == null) {
            throw new NullPointerException();
        }
    }
}
