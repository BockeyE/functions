package ObservationModel.SecondVersion;

/**
 * @author bockey
 */
public interface Observable {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObserver();
}
