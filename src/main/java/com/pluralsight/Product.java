package com.pluralsight;

public class Product {
    private double price;
    private String name;
    private String id;
    private Boolean inCart;


    public Product(String id, String name, double price) {
        this.price = price;
        this.name = name;
        this.id = id;
        this.inCart = false;


    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public void setInCart(Boolean inCart) {
        this.inCart = inCart;
    }

    @Override
    public String toString() {
        return String.format("Product [id=%s, name=%s, price=%.2f]",
                id, name, price);
    }
}
