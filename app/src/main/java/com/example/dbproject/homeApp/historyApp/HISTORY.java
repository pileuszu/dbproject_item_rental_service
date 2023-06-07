package com.example.dbproject.homeApp.historyApp;

public class HISTORY {
    public Integer history_id;
    public Integer history_item_id;
    public String history_item_name;
    public String history_start_date;
    public String history_return_date;
    public String history_return_state;

    public HISTORY() {
    }

    public Integer getHistory_id() {
        return this.history_id;
    }

    public void setHistory_id(Integer history_id) {
        this.history_id = history_id;
    }

    public Integer getHistory_item_id() {
        return history_item_id;
    }

    public void setHistory_item_id(Integer history_item_id) {
        this.history_item_id = history_item_id;
    }

    public String getHistory_item_name() {
        return history_item_name;
    }

    public void setHistory_item_name(String history_item_name) {
        this.history_item_name = history_item_name;
    }

    public String getHistory_start_date() {
        return history_start_date;
    }

    public void setHistory_start_date(String history_start_date) {
        this.history_start_date = history_start_date;
    }

    public String getHistory_return_date() {
        return history_return_date;
    }

    public void setHistory_return_date(String history_return_date) {
        this.history_return_date = history_return_date;
    }

    public String getHistory_return_state() {
        return history_return_state;
    }

    public void setHistory_return_state(String history_return_state) {
        this.history_return_state = history_return_state;
    }
}
