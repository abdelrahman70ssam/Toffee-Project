package Finance;

import StockAandOrders.* ;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

public class Cash extends PaymentMethod {
    private int paymentID;

    public Cash(Order order, int paymentID) {
        super(order);
        this.paymentID = paymentID;
    }



    public void verifyPayment() {
        System.out.println("delivery tax 50 L.E is added to the order");
        order.addtoTotalprice(50);
        System.out.println("Payment verified for order ID " + order.getOrderID() + " using cash.");
    }
}

