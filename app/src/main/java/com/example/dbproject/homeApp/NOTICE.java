package com.example.dbproject.homeApp;

public class NOTICE {
    public Integer id;
    public String title;
    public String content;
    public String writer;
    public String date;

    public NOTICE() {

    }

    // TODO : get,set 함수 생략

    public void setId(Integer _id) {
        id = _id;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public void setContent(String _content) {
        content = _content;
    }

    public void setWriter(String _writer) {
        writer = _writer;
    }

    public void setDate(String _date) {
        date = _date;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getDate() {
        return date;
    }
}
