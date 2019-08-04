package com.ajiri_algure.gstoremgt.market_data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by HP on 28/12/2018.
 */

@Entity
public class maeketItems {
    @PrimaryKey @NonNull String title;
    String description;
    String price;
    String details;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    String pics;

    public maeketItems(){}
    public  maeketItems(@NonNull String title,
                        String description,
                        String price,
                        String details, String pics){
        this.title=title;
        this.description=description;
        this.price=price;
        this.details=details;
        this.pics=pics;
    }
}
