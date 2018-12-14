package ObservationModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public void notifyObserver(Class<?> cls, Object... obs) {
        if (cls == null) {
            throw new NullPointerException();
        }

        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("update")) {
                try {
                    method.invoke(cls, obs);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
