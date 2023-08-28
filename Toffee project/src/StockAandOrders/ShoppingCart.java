package StockAandOrders;
import java.sql.Struct;
import java.util.Scanner;
import java.lang.System;
import StockAandOrders.Item;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Item> items = new ArrayList<Item>();
    private int quantityForitem;

    public ArrayList<Item> getCart()
    {
        return items;
    }

    public void addItemToCart(Item item) {
        items.add(item);
    }

    public void removeItemFromCart(ShoppingCart cart) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the ID of the item you want to remove from your cart:");
        String id = scanner.nextLine();
        ArrayList<Item> items = cart.getCart();
        boolean found = false;
        for (Item item : items) {
            if (item.getID().equals(id)) {
                found = true;
                System.out.println("Are you sure you want to remove this item from your cart? (Y/N)");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("Y")) {
                    cart.getCart().remove(item);
                    System.out.println("Item removed from cart.");
                } else {
                    System.out.println("Item not removed from cart.");
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Item not found in cart.");
        }
    }

    public int cartSize() {
        return items.size();
    }

    static public void showCart(ShoppingCart cart) {
        ArrayList<Item> items = cart.getCart();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart contains:");
            for (Item item : items) {
                System.out.println(item.toString());
            }
        }
    }

    public void deleteCart() {
        items.clear();
    }

    public boolean setQuantityForItem(int quantity , Item item)
    {
       if(item.getUnitType() == "units"){
           if(quantity > 50 || quantity > item.getQuantity()){
               System.out.println("can not buy more than 50 units per order");
               return false ;
           }
           else{
                quantityForitem = quantity ;
               item.setQuantity(item.getQuantity() - quantity)  ;
           }
       }
       else{
           if(quantity > 10 || quantity > item.getQuantity()){
               System.out.println("can not buy more than 10 kg per order");
               return false;
           }
           else{
               quantityForitem = quantity ;
               item.setQuantity(item.getQuantity() - quantity)  ;
           }
       }
        return true ;
    }


}
