package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    private static final String LOG_FILE_NAME = "receipt.csv";

    public static void main(String[] args) {
        // Create scanner to read user input

        Scanner scan = new Scanner(System.in);

        // Initialize variables
        String fileName = "products.csv";
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;
        double deposit = 0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 6) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1.) Deposit money to account");
            System.out.println("2.) Search Product by ID");
            System.out.println("3.) Show Products");
            System.out.println("4.) Show Cart");
            System.out.println("5.) Check Out");
            System.out.println("6.) Exit");

            choice = scan.nextInt();
            scan.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    deposit = depositInAccount(scan);
                    break;
                case 2:
                    System.out.println("Enter product ID:");
                    String id = scan.nextLine();

                    findProductById(id, inventory);
                    break;
                case 3:
                    String contShop = "yes";
                    while(contShop.equalsIgnoreCase("yes")) {
                        displayProducts(inventory, cart, scan);
                        System.out.println("Would you like to continue shopping?");
                        contShop = scan.nextLine();
                    }
                    break;
                case 4:
                    displayCart(cart, scan, totalAmount);
                    break;
                case 5:
                    if(cart.isEmpty()){
                        System.out.println("You have nothing in the cart");
                        break;
                    }
                    checkOut(cart, totalAmount, deposit);
                    break;
                case 6:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        /*BufferedReader reads through csv file and creates a new Product using parameters
        * in the product class.*/
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));){
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                Product product = new Product(id, name, price);
                // Adds new product to inventory Arraylist.
                inventory.add(product);

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scan) {

        /* Iterate through all the Products in the inventory ArrayList and formats them
        * into a table that is Left Aligned, in line with the headers and has an
        * approriate number of characters allowed.*/
        System.out.println("Products: ");
        System.out.printf("%-8s %-35s %-6s%n", "ID:", "Name:", "Price:");

                for (Product product : inventory) {
                    System.out.printf("%-8s %-35s %-6s%n",
                    // Fetches variables form Product Class using the getters.
                    product.getId(),
                    product.getName(),
                   "$" + product.getPrice());
                }
        System.out.println(" ");
        System.out.println("What would you like to add to your cart? ");
        System.out.println("Please enter the product ID.");

        String id = scan.nextLine();


        Product selectedProduct = null;


            for (Product product : inventory) {
                if (product.getId().equalsIgnoreCase(id)) {
                    selectedProduct = product;
                    break;
                }
            }

            // Assigns new Product to variable selectedProduct to be used more easily.
            if (selectedProduct != null) {
                System.out.println("You selected: ");
                // Keeps formatting consistent throughout.
                System.out.printf("%-8s %-35s %-6s%n", "ID:", "Name:", "Price:");
                System.out.printf("%-8s %-35s %-6s%n",
                        selectedProduct.getId(),
                        selectedProduct.getName(),
                       "$" + selectedProduct.getPrice());
                System.out.println("Would you like to add this product to your cart? (yes/no)");
                String addToCart = scan.nextLine();
                if (addToCart.equalsIgnoreCase("yes")) {
                    // Add the selected product to the cart
                    cart.add(selectedProduct);
                    System.out.printf("%-8s %-35s %-6s ",
                            selectedProduct.getId(),
                            selectedProduct.getName(),
                            selectedProduct.getPrice());
                    System.out.println(" has been added to your cart.");
                } else {
                    System.out.println("The product was not added to your cart.");
                }
            } else {
                System.out.println("No product found with ID: " + id);
            }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scan, double totalAmount) {

        // Iterates through all Products in cart ArrayList and formats appropriately for display.
        System.out.println("Products: ");
        System.out.printf("%-8s %-35s %-6s%n", "ID:", "Name:", "Price:");
        for(Product product : cart){
            System.out.printf("%-8s %-35s %-6s%n",
                    product.getId(),
                    product.getName(),
                   "$" + product.getPrice());

        }
        // Iterates through getters of Products in car ArrayList, increments them and reassigns them to a single variable.
        for(Product product : cart){
            totalAmount += product.getPrice();
        }
        System.out.println("Your total is: ");
        System.out.println(totalAmount);

        System.out.println("Would you like to remove anything?");
        String response = scan.nextLine();

        // Allows user to remove items form the cart.
        if(response.equalsIgnoreCase("yes")){

            System.out.println("Enter the item ID you would like to remove.");
            String id = scan.nextLine();

            // Decrements and reassigns totalAmount variable if products have been removed.
            for (Product product : cart) {
                if (product.getId().equalsIgnoreCase(id)) {
                    cart.remove(product);
                    totalAmount -= product.getPrice();
                    break;
                }else{
                    System.out.println("Product not in cart");
                }
            }
            // Displays final total.
            System.out.println("Your new total is " + totalAmount);
        }
    }

   public static double depositInAccount(Scanner scan){

        // Allows user to deposit money into store account for purchase of items.
        System.out.println("How much would you like to deposit?");
       return scan.nextDouble();
   }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, double deposit) {
        // Fetches current day and saves it to variable
        LocalDate today = LocalDate.now();
        ArrayList<Product> receipt = new ArrayList<>();
        Scanner scan = new Scanner(System.in);


        // Fetches total of items in cart.
        for(Product product : cart){
            totalAmount += product.getPrice();
        }

        // Displays amount in account and total of cart
        System.out.println("You have $" + deposit + " in your account");
        System.out.println("Your total is $" + totalAmount);
        System.out.println("");
        // Subtracts total of cart from amount in account and reassigns deposit variable to this value.


        // Checks if funds in account are sufficient
        if(totalAmount > deposit){
            System.out.println("Insufficient funds");
            System.out.println("");

        }else {
            deposit -= totalAmount;

            System.out.println("Current balance $" + deposit);

            // Loads items in cart ArrayList into receipt ArrayList
            for (Product product : cart) {
                product.getId();
                product.getName();
                product.getPrice();

                Product purchasedProduct = new Product(product.getId(), product.getName(), product.getPrice());
                // Adds new product to inventory Arraylist.
                receipt.add(purchasedProduct);
            }
            System.out.println("Please Enter Your Name for our records");
            String userName = scan.nextLine();
            // displays new ArrayList
            System.out.println("Receipt for " + today + ": ");
            System.out.printf("%-8s %-35s %-6s%n", "ID:", "Name:", "Price:");
            for (Product product : receipt) {
                System.out.printf("%-8s %-35s %-6s%n",
                        product.getId(),
                        product.getName(),
                        "$" + product.getPrice());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true))) {


                    StringBuilder builder = new StringBuilder("User Name: ");
                    builder.append(userName).append(", Date: ");
                    builder.append(today).append(", ID: ");
                    ;
                    builder.append(product.getId()).append(", Product Name: ");
                    builder.append(product.getName()).append(", Price: $");
                    builder.append(product.getPrice());


                    String result = builder.toString();
                    writer.write(result);
                    writer.newLine();
                } catch (Exception e) {
                    System.err.println("Error Writing to the Log File" + e.getMessage());
                }
            }


            System.out.println("Thank you for shopping!");
        }
    }
   public static void findProductById(String id, ArrayList<Product> inventory) {

       boolean found = false;

       // Checks if product ID matches Product in inventory
       while (!found) {
           // Fetches info for any products that match search.
           System.out.println("Products: ");
           System.out.printf("%-8s %-35s %-6s%n", "ID:", "Name:", "Price:");
           for (Product product : inventory) {
               if (product.getId().equalsIgnoreCase(id)) {
                   System.out.printf("%-8s %-35s %-6s%n",
                           product.getId(),
                           product.getName(),
                          "$" + product.getPrice());
                   // Reassigns "found" to stop the loop
                   found = true;
                   break;
               }
           }
       }
   }

}
