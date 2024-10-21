package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

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
                    displayProducts(inventory, cart, scan);
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



        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));){
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                Product product = new Product(id, name, price);

                inventory.add(product);

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scan) {
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.

        System.out.printf("%-8s %-35s %-6s%n", "id", "name", "price");
        System.out.println("Products: ");
                for (Product product : inventory) {
                    System.out.printf("%-8s %-35s %-6s%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice());
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

            if (selectedProduct != null) {
                System.out.println("You selected: ");
                System.out.printf("%-8s %-35s %-6s%n", "id", "name", "price");
                System.out.printf("%-8s %-35s %-6s%n",
                        selectedProduct.getId(),
                        selectedProduct.getName(),
                        selectedProduct.getPrice());
                System.out.println("Would you like to add this product to your cart? (yes/no)");
                String addToCart = scan.nextLine();
                if (addToCart.equalsIgnoreCase("yes")) {
                    cart.add(selectedProduct); // Add the selected product to the cart
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
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.

        System.out.printf("%-8s %-35s %-6s%n", "id", "name", "price");
        System.out.println("Products: ");
        for(Product product : cart){
            System.out.printf("%-8s %-35s %-6s%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice());

        }
        for(Product product : cart){
            totalAmount += product.getPrice();
        }
        System.out.println("Your total is: ");
        System.out.println(totalAmount);

        System.out.println("Would you like to remove anything?");
        String response = scan.nextLine();

        if(response.equalsIgnoreCase("yes")){

            System.out.println("Enter the item ID you would like to remove.");
            String id = scan.nextLine();

            for (Product product : cart) {
                if (product.getId().equalsIgnoreCase(id)) {
                    cart.remove(product);
                    totalAmount -= product.getPrice();
                    break;
                }else{
                    System.out.println("Product not in cart");
                }
            }
            System.out.println("Your new total is " + totalAmount);
        }
    }

   public static double depositInAccount(Scanner scan){

        System.out.println("How much would you like to deposit?");
       return scan.nextDouble();
   }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, double deposit) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.

        LocalDate today = LocalDate.now();


        for(Product product : cart){
            totalAmount += product.getPrice();
        }
        System.out.println("You have $" + totalAmount + " in your account");
        System.out.println("Your total is $" + totalAmount);
        deposit = deposit - totalAmount;
        if(deposit < 0){
            System.out.println("Insufficient funds");
            System.out.println("You have $" + deposit);
        }else{
            System.out.println("Thank you for shopping!");
            System.out.println("Current balance $" + deposit);

            System.out.printf("%-8s %-35s %-6s%n", "id", "name", "price");
            System.out.println("Products: ");
            for(Product product : cart){
                System.out.printf("%-8s %-35s %-6s%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice());

            }
            
            Receipt receipt = new Receipt(today, totalAmount);
            System.out.println(receipt);
        }




    }
   public static void findProductById(String id, ArrayList<Product> inventory) {
       // This method should search the inventory ArrayList for a product with
       // the specified ID, and return the corresponding com.pluralsight.Product object. If
       // no product with the specified ID is found, the method should return
       // null.

       boolean found = false;

       while (!found) {
           for (Product product : inventory) {
               System.out.printf("%-8s %-35s %-6s%n", "id", "name", "price");
               System.out.println("Products: ");
               if (product.getId().equalsIgnoreCase(id)) {
                   System.out.printf("%-8s %-35s %-6s%n",
                           product.getId(),
                           product.getName(),
                           product.getPrice());
                   found = true;
               }
           }
       }
   }
}