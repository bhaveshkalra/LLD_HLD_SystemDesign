package LowLevelDesign.DesignPatterns.ObserverDesignPattern;

public interface StockObservable {
    public void add(NotificationAlertObserver observer);
    public void remove(NotificationAlertObserver observer);
    public void notifySubscribers(String message);
    public void setStockCount(int newStockAdded);
    public int getStockCount();
}
