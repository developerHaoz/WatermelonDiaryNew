package com.lizehao.watermelondiarynew.bean;

/**
 * Created by 李 on 2017/1/26.
 */
public class DiaryBean {

    private String date;
    private String title;
    private String content;

    public DiaryBean(String date, String title, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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






}
