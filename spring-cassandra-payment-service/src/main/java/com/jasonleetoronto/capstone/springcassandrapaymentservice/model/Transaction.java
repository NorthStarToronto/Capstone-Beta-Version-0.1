package com.jasonleetoronto.capstone.springcassandrapaymentservice.model;

import org.springframework.data.cassandra.core.mapping.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/* Define Transaction Entity with the attributes and Map it to the Transaction Cassandra Table and related columns  */
@Entity
@Table(value = "transaction")
public class Transaction {

    @PrimaryKey
    private TransactionKey transactionKey;

    @Column(value = "brand")
    private String brand;

    /* Add Http Request Body Constraints on the Transaction Bean */
    @Size(max=4, message = "Category should not have more than 4 characters")
    @Column(value = "category")
    private String category;

    @Column(value = "company")
    private String company;

    @Column(value = "dept")
    private String dept;

    @Column(value = "product_measure")
    private String productMeasure;

    @Column(value = "product_size")
    private String productSize;

    @Column(value = "purchase_amount")
    private Float purchaseAmount;

    @Column(value = "purchase_quantity")
    private int purchaseQuantity;

    /* Default No Argument Constructor */
    protected Transaction() {
    }

    public Transaction(TransactionKey transactionKey, String dept, String category, String company, String brand, String productSize, String productMeasure, int purchaseQuantity, Float purchaseAmount) {
        super();
        this.transactionKey = transactionKey;
        this.brand = brand;
        this.category = category;
        this.company = company;
        this.dept = dept;
        this.productMeasure = productMeasure;
        this.productSize = productSize;
        this.purchaseAmount = purchaseAmount;
        this.purchaseQuantity = purchaseQuantity;
    }

    /* Getters and Setters */
    public TransactionKey getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(TransactionKey transactionKey) {
        this.transactionKey = transactionKey;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductMeasure() {
        return productMeasure;
    }

    public void setProductMeasure(String productMeasure) {
        this.productMeasure = productMeasure;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Float getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Float purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    /* toString method */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionKey=" + transactionKey +
                ", dept='" + dept + '\'' +
                ", category='" + category + '\'' +
                ", company='" + company + '\'' +
                ", brand='" + brand + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productMeasure='" + productMeasure + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                ", purchaseAmount=" + purchaseAmount +
                '}';
    }
}
