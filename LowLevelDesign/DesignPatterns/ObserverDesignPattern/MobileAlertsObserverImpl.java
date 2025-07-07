package LowLevelDesign.DesignPatterns.ObserverDesignPattern;

public class MobileAlertsObserverImpl implements NotificationAlertObserver {

    String name;
    StockObservable observable;

    public MobileAlertsObserverImpl(String name, StockObservable obs) {
        this.name = name;
        this.observable = obs;
    }

    @Override
    public void update(String msg) {
        msg = (observable.getStockCount() == 0 ? msg + "." : msg + "with stocks =" + observable.getStockCount());
        sendSMS(name , "Product is in stock hurry up," + msg);
    }

    private void sendSMS(String username, String msg) {
        // TODO Auto-generated method stub
        System.out.println("SMS sent to:" + username + ","+ msg);
    }
    
}
