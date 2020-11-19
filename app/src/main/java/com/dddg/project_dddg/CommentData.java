package com.dddg.project_dddg;

public class CommentData {
    public String id;
    public String context;
    public String time;
    public Boolean anonymouse;



    public CommentData() {
    }

    public CommentData(String id, String context, String time,Boolean anonymouse) {
        this.id = id;
        this.context = context;
        this.time = time;
        this.anonymouse = anonymouse;
    }

    public CommentData(String id, String context, String time) {
        this.id = id;
        this.context = context;
        this.time = time;
        anonymouse = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Boolean getAnonymouse() {
        return anonymouse;
    }

    public void setAnonymouse(Boolean anonymouse) {
        this.anonymouse = anonymouse;
    }
}
