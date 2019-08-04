package com.ajiri_algure.gstoremgt;

/**
 * Created by HP on 12/01/2019.
 */

public class Seller {
    public String name;
    public String brand;
    public String address;
    public String city;
    public String email;
    public String phone;
    public String paystack;
    public String extras;

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public Seller(){}
public Seller(String name,
              String brand,
              String city,
              String email,
              String phone,
              String paystack){
        this.name=name;
        this.brand=brand;
        this.city=city;
        this.email=email;
        this.phone=phone;
        this.paystack=paystack;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaystack() {
        return paystack;
    }

    public void setPaystack(String paystack) {
        this.paystack = paystack;
    }
}
