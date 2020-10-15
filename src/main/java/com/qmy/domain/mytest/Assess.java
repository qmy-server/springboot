package com.qmy.domain.mytest;
//评价对象
public class Assess {
    private int id;
    private String username;
    private String productName;
    private int productId;
    private String content;
    private float rate1;
    private float rate2;
    private float rate3;
    private float rateSum;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getRateSum() {
        return rateSum;
    }

    public void setRateSum(float rateSum) {
        this.rateSum = rateSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRate1() {
        return rate1;
    }

    public void setRate1(float rate1) {
        this.rate1 = rate1;
    }

    public float getRate2() {
        return rate2;
    }

    public void setRate2(float rate2) {
        this.rate2 = rate2;
    }

    public float getRate3() {
        return rate3;
    }

    public void setRate3(float rate3) {
        this.rate3 = rate3;
    }
}
