package LowLevelDesign.DesignPatterns.FacadeDesignPattern;

import LowLevelDesign.DesignOrderManagementSystem.Product;
import LowLevelDesign.DesignPatterns.FacadeDesignPattern.OrderSystem.Invoice;
import LowLevelDesign.DesignPatterns.FacadeDesignPattern.OrderSystem.Notification;
import LowLevelDesign.DesignPatterns.FacadeDesignPattern.OrderSystem.Payment;
import LowLevelDesign.DesignPatterns.FacadeDesignPattern.OrderSystem.ProductDAO;

public class OrderFacade { 
    ProductDAO productDAO;
    Payment payment;
    Invoice invoice;
    Notification notification;

    public OrderFacade() {
        productDAO = new ProductDAO();
        payment = new Payment();
        invoice = new Invoice();
        notification = new Notification();
    }

    public void createOrder() {
        Product product = productDAO.getProduct(121);
        payment.makePayment();
        invoice.generateInvoice();
        notification.sendNotification();
    }
}

class OrderClient {
    public static void main(String[] args) {
        OrderFacade orderFacade = new OrderFacade();
        orderFacade.createOrder();
    }
}