package Finance;
import StockAandOrders.* ;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;
public abstract class PaymentMethod {
    protected Order order;

    public PaymentMethod(Order order) {
        this.order = order;
    }

    public abstract void verifyPayment();
}