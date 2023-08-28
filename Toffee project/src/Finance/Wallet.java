package Finance;
import Customer.* ;
import StockAandOrders.*;
import Finance.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;


public class Wallet extends PaymentMethod {
    private int paymentID;

    public Wallet(Order order, int paymentID) {
        super(order);
        this.paymentID = paymentID;
    }

    public void verifyPayment() {
        System.out.println("Payment verified for order ID " + order.getOrderID() + " using wallet.");
    }


}