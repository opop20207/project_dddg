package com.dddg.project_dddg;

public class NewsData {
    public String title;
    public String context;
    public String img_url;
    public String news_Url;
    public String info;
    public  NewsData(){

    }
    public NewsData(String title, String context,String img_url, String news_Url,String info) {
        this.title = title;
        this.context = context;
        this.img_url = img_url;
        this.news_Url = news_Url;
        this.info = info;
    }
}
