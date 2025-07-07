package LowLevelDesign.DesignPatterns.ObserverDesignPattern;

public class EmailAlertsObserverImpl implements NotificationAlertObserver {
    String emailId;
    StockObservable observable;

    public EmailAlertsObserverImpl(String emailId, StockObservable obs) {
        this.emailId = emailId;
        this.observable = obs;
    }

    @Override
    public void update(String msg) {
        msg = (observable.getStockCount() == 0 ? msg + "." : msg + "with stocks =" + observable.getStockCount());
        sendEmail(emailId , "Product is in stock hurry up," + msg);
    }

    private void sendEmail(String emailId2, String msg) {
        // TODO Auto-generated method stub
        System.out.println("Mail sent to" + emailId2 + ","+ msg);
    }
    
}
