package com.example.dbproject.DB_TABLE;

public class ITEM_CATEGORY {

    public String item_category;
    public String item_location;
    public Integer item_total_amount;
    public Integer item_left_amount;

    public ITEM_CATEGORY() {

    }

    public Integer getItem_total_amount() {
        return item_total_amount;
    }

    public void setItem_total_amount(Integer item_total_amount) {
        this.item_total_amount = item_total_amount;
    }

    public String getItem_location() {
        return item_location;
    }

    public void setItem_location(String item_location) {
        this.item_location = item_location;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }

    public Integer getItem_left_amount() {
        return item_left_amount;
    }

    public void setItem_left_amount(Integer item_left_amount) {
        this.item_left_amount = item_left_amount;
    }
}
