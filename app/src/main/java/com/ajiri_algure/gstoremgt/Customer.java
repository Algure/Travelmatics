package com.ajiri_algure.gstoremgt;

/**
 * Created by HP on 05/01/2019.
 */

public class Customer {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gcoins;
    private String id;
    private String favorites;
    private String orders;
    private String Cart;
    private String extras;

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getCart() {
        return Cart;
    }

    public void setCart(String cart) {
        Cart = cart;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getGcoins() {
        return gcoins;
    }

    public void setGcoins(String gcoins) {
        this.gcoins = gcoins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    public Customer(){}

    public Customer(String name, String email, String phone, String address, String password, String gcoins){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.password=password;
        this.gcoins=gcoins;
    }
    public Customer(String email, String pass, String id){
        this.email=email;
        this.password=pass;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
