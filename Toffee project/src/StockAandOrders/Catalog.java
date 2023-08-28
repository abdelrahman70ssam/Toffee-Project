package StockAandOrders;
import StockAandOrders.Item;

import java.util.ArrayList;
import java.lang.System;
import java.util.Scanner;

import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;


public class Catalog {
    private ArrayList<Item> items = new ArrayList<Item>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> searchItemByName(String name) {
        ArrayList<Item> matchingItems = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    public ArrayList<Item> searchItemByID(String id) {
        ArrayList<Item> matchingItems = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getID().equals(id)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    public ArrayList<Item> searchItemByCategory(String category) {
        ArrayList<Item> matchingItems = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getCategory().equals(category)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    public ArrayList<Item> searchItemByBrand(String brand) {
        ArrayList<Item> matchingItems = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getBrand().equals(brand)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }


    public Item selectItem(String id) {
        for (Item item : items) {
            if (item.getID().equals(id)) {
                return item;
            }
        }
        return null;
    }

   public void displayMatchingItems(ArrayList<Item> matcheditems)
    {
        int num = 1;
        for (Item item:matcheditems) {
            System.out.println("Item # " + num );
            viewItemInfo(item);
            ++num;
        }
    }

    public void viewItemInfo(Item item) {
        System.out.println("Name: " + item.getName());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Brand: " + item.getBrand());
        System.out.println("Unit type: " + item.getUnitType());
        System.out.println("Discount: " + item.getDiscount());
        System.out.println("Status: " + item.getStatus());
        System.out.println("Description: " + item.getDescription());
        System.out.println("Price: " + item.getPrice());
        System.out.println("ID: " + item.getID());
        System.out.println("Quantity: " + item.getQuantity());

    }


    public ArrayList<Item> getCatalog() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("catalog.txt"));
            String line = reader.readLine(); // read the header line and ignore it
            line = reader.readLine();
            while (line != null) {
                String[] itemArray = line.split(",");
                Item item = new Item();
                item.setName(itemArray[0].trim());
                item.setCategory(itemArray[1].trim());
                item.setBrand(itemArray[2].trim());
                item.setUnitType(itemArray[3].trim());
                item.setDiscount(Integer.parseInt(itemArray[4].trim()));
                item.setStatus(itemArray[5].trim());
                item.setDescription(itemArray[6].trim());
                item.setPrice(Double.parseDouble(itemArray[7]));
                item.setID(itemArray[8].trim());
                item.setQuantity(Integer.parseInt(itemArray[9].trim()));
                items.add(item);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return items;
    }

}


