package model;
import java.util.*;
public class GameEventManager implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private List<ExplosionEvent> explosionEvents = new ArrayList<>();
    @Override
    public void attach(Observer o) { observers.add(o); }
    @Override
    public void detach(Observer o) { observers.remove(o); }
    @Override
    public void notifyObservers() {
        for (Observer o : observers) o.update();
    }

    // --- Explosion events ---
    public void notifyExplosion(ExplosionEvent e) {
        explosionEvents.add(e);
        notifyObservers();  // tell observers to refresh
    }

    public List<ExplosionEvent> getExplosionEvents() {
        return new ArrayList<>(explosionEvents);
    }

    public void clearExplosionEvents() {
        explosionEvents.clear();
    }
}
