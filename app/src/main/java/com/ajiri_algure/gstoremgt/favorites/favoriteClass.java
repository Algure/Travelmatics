package com.ajiri_algure.gstoremgt.favorites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ajiri_algure.gstoremgt.market_data.maeketItems;

/**
 * Created by HP on 07/01/2019.
 */
@Entity
public class favoriteClass  {


    @PrimaryKey @NonNull String fireId;
    String title;
    String description;
    String price;
    String pics;

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj){
            return true;
        }
        if(!(obj instanceof favoriteClass )){
            return false;
        }
        return (title.equals(((favoriteClass) obj).getTitle())&& description.equals(((favoriteClass) obj).getDescription())
        && price.equals(((favoriteClass) obj).getPrice())
        && fireId.equals(((favoriteClass) obj).getFireId()));
    }

    @Override
    public int hashCode() {
        return title.length()*23;
    }

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

    String details;
    public favoriteClass(maeketItems favm, String favid) {
        this.title=favm.getTitle();
        this.description=favm.getDescription();
        this.price=favm.getPrice();
        this.details=favm.getDetails();
        this.fireId=favid;
        this.pics=favm.getPics();
    }

public favoriteClass(){}

   public String getFireId() {
        return fireId;
    }

      public void setFireId(String fireId) {
        this.fireId = fireId;
    }


}
