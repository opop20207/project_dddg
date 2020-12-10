package com.dddg.project_dddg;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {
    public ArrayList<TeamData> myTeamList;
    public ArrayList <Pair<CommentData,String>>commentList;//<comment, key>
    public ArrayList<String>freeBoardList;
    public int point;

    public UserData() {
        myTeamList = new ArrayList<>();
        commentList = new ArrayList<>();
        freeBoardList = new ArrayList<>();
        point  = 0;
    }
    public void freeboardAdd(String a){
        freeBoardList.add(a);
    }
    public void commentAdd(Pair<CommentData,String> a){
        commentList.add(a);
    }
    public UserData(ArrayList<TeamData> myTeamList, ArrayList<Pair<CommentData, String>> commentList, ArrayList< String> freeBoardList, int point) {
        this.myTeamList = myTeamList;
        this.commentList = commentList;
        this.freeBoardList = freeBoardList;
        this.point = point;
    }
    HashMap<String,Object> toMap(){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("myTeamList",myTeamList);
        map.put("commentList",commentList);
        map.put("freeBoardList",freeBoardList);
        map.put("point",point);
        return map;
    }
    public ArrayList<TeamData> getMyTeamList() {
        return myTeamList;
    }

    public void setMyTeamList(ArrayList<TeamData> myTeamList) {
        this.myTeamList = myTeamList;
    }

    public ArrayList<Pair<CommentData, String>> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Pair<CommentData, String>> commentList) {
        this.commentList = commentList;
    }

    public ArrayList<String> getFreeBoardList() {
        return freeBoardList;
    }

    public void setFreeBoardList(ArrayList<String> freeBoardList) {
        this.freeBoardList = freeBoardList;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
