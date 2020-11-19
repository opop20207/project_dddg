package com.dddg.project_dddg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FreeboardData {
    public String title;
    public String context;
    public String date;
    public String email;
    public ArrayList<CommentData> comment;
    public boolean anonymouse;
    public FreeboardData() {
        title = new String();
        context = new String();
        date = new String();
        email = new String();
        comment = new ArrayList<CommentData>();
    }
    public FreeboardData(String title, String context, String date, String email, ArrayList<CommentData> comment,boolean anonymouse) {
        this.title = title;
        this.context = context;
        this.date = date;
        this.email = email;
        this.comment = comment;
        this.anonymouse = anonymouse;
    }

    public String getTitle() {
        return title;
    }
    public String denseTitleget() {
        if(title.length()>25) return title.substring(0,25)+"...";
        else return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String denseContextget() {
        String returnContext = context;
        returnContext = returnContext.replaceAll("\n"," ");
        if( returnContext.length()>100) return  returnContext.substring(0,100)+"...";
        else return  returnContext;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<CommentData> getComment() {
        return comment;
    }

    public void setComment(ArrayList<CommentData> comment) {
        this.comment = comment;
    }
    public void addComment(CommentData commentData){
        if(comment == null) comment = new ArrayList<>();
        comment.add(commentData);
    }
    public void delComment(int position){
        comment.remove(position);
    }
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("title",title);
        map.put("context",context);
        map.put("date",date);
        map.put("email",email);
        map.put("comment",comment);
        map.put("anonymouse",anonymouse);
        return map;
    }
}
