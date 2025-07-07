package LowLevelDesign.DesignPatterns.FacadeDesignPattern;

import LowLevelDesign.DesignOrderManagementSystem.Product;

public class OrderSystem {
    class ProductDAO {
        public Product getProduct(int id) {
            return new Product();
        }
    }

    class Payment {
        public boolean makePayment() {
            return true;
        }
    }

    class Invoice {
        public void generateInvoice(){
            //generate invoice
        }
    }

    class Notification {
        public void sendNotification(){
            //send the noti
        }
    }
}
