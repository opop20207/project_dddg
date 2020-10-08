package com.dddg.project_dddg;

public class NewsData {
    String title;
    String context;
    String img_url;
    String news_Url;
    public  NewsData(){

    }
    public NewsData(String title, String context,String img_url, String news_Url) {
        this.title = title;
        this.context = context;
        this.img_url = img_url;
        this.news_Url = news_Url;
    }
}
