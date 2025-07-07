package LowLevelDesign.DesignPatterns.ObserverDesignPattern;

public class Store {
    public static void main(String[] args) {
        StockObservable iphoneStockObservable = new IphoneObservableImpl();

        NotificationAlertObserver n1 = new EmailAlertsObserverImpl("abc@gmail.com", iphoneStockObservable);
        NotificationAlertObserver n2 = new EmailAlertsObserverImpl("xyz@gmail.com", iphoneStockObservable);
        NotificationAlertObserver n3 = new MobileAlertsObserverImpl("Bhavesh Kalra", iphoneStockObservable);
        NotificationAlertObserver n4 = new MobileAlertsObserverImpl("Shivani Sharma", iphoneStockObservable);
        iphoneStockObservable.add(n1);
        iphoneStockObservable.add(n2);
        iphoneStockObservable.add(n3);
        iphoneStockObservable.add(n4);
        iphoneStockObservable.setStockCount(20);
        iphoneStockObservable.setStockCount(0);
        iphoneStockObservable.setStockCount(100);
    }
    
}
