package com.pluralsight;

import java.time.LocalDate;

public class Receipt {
    private LocalDate orderDate;
    private Product product;
    private double salesTotal;

    public Receipt(LocalDate orderDate, double salesTotal) {
        this.orderDate = orderDate;
        this.product = product;
        this.salesTotal = salesTotal;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSalesTotal() {
        return salesTotal;
    }

    public void setSalesTotal(double salesTotal) {
        this.salesTotal = salesTotal;
    }
}
