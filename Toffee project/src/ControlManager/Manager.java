package ControlManager;
import Customer.* ;
import StockAandOrders.*;
import Finance.*;

import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.* ;


/**
 * class Manager that controls and manipulates the system
 * @author ABDELRAHMAN
 *
 */
public class Manager {

    private User loginuser = new User();
    private User newuser = new User();
    private ShoppingCart cart = new ShoppingCart();
    private Catalog menu = new Catalog();
    private Order newOrder = new Order();


    /**
     * Displays the main menu of the system.
     * @author ABDELRAHMAN
     */
    public void displayMainMenu() {
        Manager sys = new Manager();
        Scanner in = new Scanner(System.in);

        int choice = 0;
        while (true) {
            System.out.println("Welcome to TOFFEE Online Store :) ");
            System.out.println("------------------------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Catalog");
            System.out.println("4. manage cart");
            System.out.println("5. checkout ");
            System.out.println("0. TO Exit Program");
            System.out.println("------------------------");

            choice = in.nextInt();
            in.nextLine();

            if (choice == 1) {
                register();
            } else if (choice == 2) {
                login();
            } else if (choice == 3) {
                viewCatalog();
            } else if (choice == 4) {
                manageCart();
            } else if (choice == 5) {
                checkOut();
            } else if (choice == 0) {
                System.out.println("Thank you for visiting our Store .");
                break;
            }
        }

    }

    /**
     * Performs the login operation.
     *
     */
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        loginuser.setEmail(email);
        loginuser.setPassword(password);

        if (searchUser(loginuser)) {

            if (loginuser.getStatus().equals("OK")) {
                System.out.println("Login successfully ");
                loginuser.setLoggedIN(true);
                System.out.println("here are 10 free loyalty points for logging ");
                loginuser.setLoyaltyPoints(10);
            } else {
                System.out.println("Login successfully but you are suspended ");
                loginuser.setLoggedIN(false);
            }
        } else {
            System.out.println("forgotten Password ?! choose (y) or (n) ");
            String option = scanner.nextLine();

            if (option.equals("y") || option.equals("Y")) {

                String savedotp = sendOTP(newuser);
                System.out.println("enter OTP send to your email : ");
                String  enteredotp = scanner.nextLine();

                if (verifyOTP(loginuser, savedotp ,enteredotp)) {
                    System.out.println("OTP is verified successfully for " + loginuser.getEmail());
                    System.out.println("enter new password ");
                    String pass = scanner.nextLine();
                    saveNewPassword(loginuser, pass);
                    loginuser.setLoggedIN(true);
                    System.out.println("login Successfully ");
                } else {
                    System.out.println("OTP verification failed!");
                }

            }
        }
    }

    /**
     * Performs the registration operation.
     */
    public void register() {
        Scanner scanner = new Scanner(System.in);
        newuser = fillRegisterForm();
        if (validateRegisterInfo(newuser)) {

            String savedotp = sendOTP(newuser);
            System.out.println("enter OTP send to your email : ");
            String  enteredotp = scanner.nextLine();

            if (verifyOTP(newuser, savedotp , enteredotp )) {
                System.out.println("OTP is verified successfully for " + newuser.getEmail());
                storeAccountInfo(newuser);
                newuser.setLoggedIN(true);
                System.out.println("Registered Successfully ");
            } else {
                System.out.println("OTP verification failed!");
            }
        }

    }

    /**
     * Fills the registration form to create a new user.
     *
     * @return The newly created user object.
     */
    public User fillRegisterForm() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phone);

        String governorate, district, street, building_no;
        System.out.println("Enter Address Info: ");
        System.out.println("Enter Governorate: ");
        governorate = scanner.nextLine();
        System.out.println("Enter District: ");
        district = scanner.nextLine();
        System.out.println("Enter street: ");
        street = scanner.nextLine();
        System.out.println("Enter building no : ");
        building_no = scanner.nextLine();

        UserAddress address = new UserAddress(governorate, district, street, building_no);
        user.setAddress(address);

        return user;

    }

    /**
     * Sends an OTP (One-Time Password) to the user's email address.
     *
     * @param user The user to whom the OTP will be sent.
     * @return The OTP sent to the user.
     */
    private String sendOTP(User user) {

        // Generate a 6-digit random OTP
        int otp = new Random().nextInt(900_000) + 100_000;
        String OTP = Integer.toString(otp);

        // Set up the properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bodyhossam62@gmail.com", "xhylrvxblcyajrmg");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress("your-gmail-username"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            // Set the subject and content of the message
            message.setSubject("Your OTP for login verification On Toffee store");
            message.setText("Hello " + user.getName() + ",\n\nYour OTP for login verification is: " + otp);

            // Send the message
            Transport.send(message);

            System.out.println("OTP sent to " + user.getEmail() + " successfully!");

        } catch (MessagingException ex) {
            ex.printStackTrace();
            System.out.println("Error while sending OTP to " + user.getEmail() + ": " + ex.getMessage());
        }
        return OTP;
    }


    /**
     * Verifies the OTP (One-Time Password) entered by the user.
     *
     * @param user     The user to whom the OTP was sent.
     * @param savedOTP The OTP that was sent to the user.
     * @param otp      The OTP entered by the user.
     * @return True if the OTP is verified successfully, false otherwise.
     */
    private boolean verifyOTP(User user, String savedOTP , String otp) {

        if (user == null) {
            System.out.println("Invalid User!");
            return false;
        }


        if (savedOTP == null || savedOTP.isEmpty()) {
            System.out.println("OTP is not generated for this user!");
            return false;
        }

        if (savedOTP.equals(otp)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves the new password for the user.
     *
     * @param user     The user for whom the password will be updated.
     * @param password The new password to be set.
     */
    public void saveNewPassword(User user, String password) {
        Scanner scanner = new Scanner(System.in);
        try {
            // create a file object for the users.txt file
            File file = new File("users.txt");

            // create a new temporary file
            File tempFile = new File("temp.txt");

            // create a BufferedReader to read from the original file
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // create a PrintWriter to write to the temporary file
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

            // read each line from the original file
            String line = null;
            while ((line = reader.readLine()) != null) {

                // split the line into its fields
                String[] fields = line.split(",");

                // check if the email matches the user's email
                if (fields[1].equals(user.getEmail())) {

                    // update the password field with the new password
                    fields[2] = password;

                    // join the fields back into a comma-separated string
                    line = String.join(",", fields);
                }

                // write the line to the temporary file
                writer.println(line);
            }

            // close the reader and writer
            reader.close();
            writer.close();

            // delete the original file
            if (!file.delete()) {
                throw new IOException("Could not delete file");
            }

            // rename the temporary file to the original file name
            if (!tempFile.renameTo(file)) {
                throw new IOException("Could not rename file");
            }

            System.out.println("Password updated successfully!");

        } catch (IOException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

    /**
     * Searches for a user in the system.
     *
     * @param user The user to search for.
     * @return True if the user is found, false otherwise.
     */
    public boolean searchUser(User user) {
        Scanner scanner = new Scanner(System.in);
        boolean userFound = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] userArray = line.split(",");
                if (user.getEmail().equals(userArray[0]) && user.getPassword().equals(userArray[1])) {
                    userFound = true;
                    user.setName(userArray[2]);
                    UserAddress address = new UserAddress(userArray[3], userArray[4], userArray[5], userArray[6]);
                    user.setAddress(address);
                    user.setStatus(userArray[7]);
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return userFound;
    }


    /**
     * stores user's account info
     * @param user
     *
     */

    private void storeAccountInfo(User user) {
        Scanner scanner = new Scanner(System.in);
        user.setStatus("OK");

        // Validate data before writing to file
        if (user.getName() == null || user.getEmail() == null || user.getAddress() == null) {
            System.out.println("User data is invalid. Cannot store account.");
            return;
        }
        String userData = user.getEmail() + "," + user.getPassword() + "," + user.getName() + "," + user.getAddress().getGovernorate()
                + "," + user.getAddress().getDistrict() + "," + user.getAddress().getStreet() + "," +
                user.getAddress().getBuildingNo() + "," + user.getStatus() + "\n";

//        System.out.println(userData);
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.append(userData);
            writer.flush();
            writer.close();
            System.out.println("User account stored successfully");
        } catch (IOException e) {
            System.out.println("Error storing user account: " + e.getMessage());
        }
    }





    /**
     * Validates the registration information provided by the user.
     *
     * @param obj The user object containing the registration information.
     * @return True if the registration information is valid, false otherwise.
     */
    public boolean validateRegisterInfo(User obj) {
        Scanner scanner = new Scanner(System.in);
        while (obj.getName() == null || !obj.getName().matches("_?[a-zA-Z]+_?[a-zA-Z]+_?")) {
            System.out.println("Please enter a valid name.");
            obj.setName(scanner.nextLine());
        }

        while (obj.getEmail() == null || !obj.getEmail().matches("[a-zA-Z0-9]+\\.?[a-zA-Z-0-9]+@[a-zA-Z-]+\\.(com)")) {
            System.out.println("Please enter a valid email address.");
            obj.setEmail(scanner.nextLine());
            ;
        }

        while (obj.getPassword() == null || obj.getPassword().isEmpty() || obj.getPassword().length() < 8) {
            System.out.println("Please enter a password.");
            obj.setPassword(scanner.nextLine());
            if (obj.getPassword().length() < 8) {
                System.out.println("Password should be at least 8 characters long.");

            }
        }

        while (obj.getPhoneNumber() == null || !obj.getPhoneNumber().matches("(01)\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
            System.out.println("Please enter a valid phone number.");
            obj.setPhoneNumber(scanner.nextLine());
        }

        return true;
    }



    /**
     * Displays the catalog of items available in the store.
     */
    public void viewCatalog() {
        Scanner scanner = new Scanner(System.in);
        menu.setItems(menu.getCatalog());

        while (true) {
            System.out.println("Filter products by :");
            System.out.println("1. search items by name");
            System.out.println("2. search items by id");
            System.out.println("3. search items by category");
            System.out.println("4. search items by brand");
            System.out.println("5. view all items");
            System.out.println("6. TO exit catalog");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                System.out.println("enter name");
                String name = scanner.nextLine();
                for (Item item : menu.searchItemByName(name)) {
                    System.out.println(item.toString());
                }
            } else if (choice == 2) {
                System.out.println("enter id");
                String id = scanner.nextLine();
                for (Item item : menu.searchItemByID(id)) {
                    System.out.println(item.toString());
                }
            } else if (choice == 3) {
                System.out.println("enter category ");
                for (Item item : menu.searchItemByCategory(scanner.nextLine())) {
                    System.out.println(item.toString());
                }
            } else if (choice == 4) {
                System.out.println("enter brand");
                for (Item item : menu.searchItemByBrand(scanner.nextLine())) {
                    System.out.println(item.toString());
                }
            } else if (choice == 5) {
                System.out.println("all items available in store");
                for (Item item : menu.getItems()) {
                    System.out.println(item.toString());
                }

            } else if (choice == 6) {
                break;
            } else {
                System.out.println("invalid number , please enter number from 1 to 6 ");
            }

            System.out.println("Do you want to select specific item ?");
            System.out.println("y or n");

            if (scanner.nextLine().equals("y")) {
                if (!loginuser.isLoggedIN()) {
                    System.out.println("want to proceed, you should login or register if you dont have an account");
                } else {
                    System.out.println("enter item id");
                    Item item = menu.selectItem(scanner.nextLine());
                    System.out.println("1. show items description or  2. add to cart");

                    int num = scanner.nextInt();
                    if (item != null) {
                        if (num == 1) {
                            item.toString();
                        } else {
                            System.out.println("set quantity for product");
                            int quantity = scanner.nextInt();
                            if(cart.setQuantityForItem(quantity , item )){
                                cart.addItemToCart(item);
                                System.out.println("item added to your shopping cart");
                            }

                        }
                    } else {
                        System.out.println("not available in store right now ");
                    }
                }

            }

        }

    }


    /**
     * Manages the user's shopping cart.
     */
    public void manageCart()
    {
        Scanner scanner = new Scanner(System.in);
        if (!loginuser.isLoggedIN())
        {
            System.out.println("want to proceed, you should login or register if you dont have an account");
            return ;
        }

        while (true)
        {
            System.out.println("Please choose an option:");
            System.out.println("1. Show cart contents");
            System.out.println("2. Remove item from cart");
            System.out.println("0. Back to main menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                cart.showCart(cart);

            } else if (choice == 2) {
                cart.removeItemFromCart(cart);

            } else if (choice == 0) {
                System.out.println("Back to main menu");
                break;
            }
            else {
                System.out.println("Invalid option, please try again.");
            }
        }
    }



    /**
     * Manages the user's checkout process.
     */
   public void checkOut()
    {

        Scanner scanner = new Scanner(System.in);
        if (!loginuser.isLoggedIN()) {
            System.out.println("Want to proceed? You should login or register if you don't have an account.");
            return;
        }

        // Check if cart has at least one product
        if (cart.getCart().isEmpty()) {
            System.out.println("Should have at least one product in cart.");
            return;
        }
        int id = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
        newOrder.setOrderID(id);
        newOrder.setCart(cart);
        newOrder.setUser(loginuser);

        System.out.println("\"specify shipping address\" : \n press 1 to enter new address or 2 to use address in your account");
        int option = scanner.nextInt();
        scanner.nextLine();

        if(option == 1){
            String governorate, district, street, building_no;
            System.out.println("Enter Address Info: ");
            System.out.println("Enter Governorate: ");
            governorate = scanner.nextLine();
            System.out.println("Enter District: ");
            district = scanner.nextLine();
            System.out.println("Enter street: ");
            street = scanner.nextLine();
            System.out.println("Enter building no : ");
            building_no = scanner.nextLine();

            UserAddress Shipaddress = new UserAddress(governorate, district, street, building_no);
            newOrder.setShippingAddress(Shipaddress);

        }
        else{
            newOrder.setShippingAddress(loginuser.getAddress());
        }

        redeemOptions();
        choosePaymentMethod();
        System.out.println("confirm Order (y) or (n) ");
        String confirm = scanner.nextLine();
        if(confirm.equals("y") || confirm.equals("Y")){
            System.out.println("order confirmed");
            newOrder.showOrderDetails(cart);
            loginuser.saveOrder(newOrder);
        }
        else{
            System.out.println("order cancelled");
        }

        System.out.println("__________________________________________________");
    }

    /**
     * Manages the user's payment method.
     */
    public void choosePaymentMethod()
    {
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("Choose Payment method (1) Cash  (2) E-Wallet ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if(option == 1){
            int id = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
            PaymentMethod paymentMethod = new Cash(newOrder , id );
            paymentMethod.verifyPayment();
            newOrder.setPaymentMethod(paymentMethod);
        }
        else{
            int id = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
            PaymentMethod paymentMethod = new Wallet(newOrder , id );
            paymentMethod.verifyPayment();
            newOrder.setPaymentMethod(paymentMethod);
        }
    }


    /**
     * Manages the user's reedem options
     */

    public void redeemOptions()
    {
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("Do you want to redeem vouchers (1) or use loyalty points? (2) or neither (0)");
        int answer = scanner.nextInt();
        scanner.nextLine();
        if(answer == 0){
            System.out.println("ok , as you like");
            return;
        }
        if(answer == 1){
            if(loginuser.getVouchers().isEmpty()){
                System.out.println("you dont have any vouchers to use");
                return ;
            }
            Voucher voucher = loginuser.getVouchers().get(loginuser.getVouchers().size()-1);
            loginuser.redeemVoucher(voucher);
            newOrder.useVoucher(voucher);

        }
        else if(answer == 2){
            System.out.println("your Loyalty points in your account = " + loginuser.getLoyaltyPoints());
            System.out.println("choose amount of loyalty points to use");
            int amount = scanner.nextInt() ;
            scanner.nextLine();
                if(loginuser.getLoyaltyPoints() < amount){
                    System.out.println("you dont have any loyalty Points to use");
                    return;
                }
                newOrder.useLoyaltyPoints( amount);
        }
    }







}
