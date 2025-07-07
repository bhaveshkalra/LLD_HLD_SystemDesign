package LowLevelDesign.DesignPatterns.ObserverDesignPattern;

import java.util.ArrayList;
import java.util.List;

public class IphoneObservableImpl implements StockObservable {
    public List<NotificationAlertObserver> observerList = new ArrayList<>();
    public int stockCount = 0
    ;
    @Override
    public void add(NotificationAlertObserver observer) {
        observerList.add(observer);
    }
    @Override
    public void remove(NotificationAlertObserver observer) {
        observerList.remove(observer);
    }
    @Override
    public void notifySubscribers(String msg) {
        for (NotificationAlertObserver observer: observerList) {
            observer.update(msg);
        }
    }
    @Override
    public void setStockCount(int newStockAdded) {
        if (stockCount == 0) {
            notifySubscribers("Out of Stock");
        }
        stockCount += newStockAdded;
        notifySubscribers("New Stock added");
    }
    @Override
    public int getStockCount() {
        return stockCount;
    }
    
}
