package com.example.dbproject.DB_TABLE;

public class PROPOSAL {
    public Integer id;
    public String title;
    public String content;
    public String write_date;
    public String write_time;
    public String response_state;
    public String response_content;

    public PROPOSAL() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public String getWrite_time() {
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }

    public String getResponse_state() {
        return response_state;
    }

    public void setResponse_state(String response_state) {
        this.response_state = response_state;
    }

    public String getResponse_content() {
        return response_content;
    }

    public void setResponse_content(String response_content) {
        this.response_content = response_content;
    }
}
