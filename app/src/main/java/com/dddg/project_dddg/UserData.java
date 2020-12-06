package com.dddg.project_dddg;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {
    public ArrayList<String> myTeamList;
    public ArrayList <Pair<CommentData,String>>commentList;//<comment, key>
    public ArrayList<Pair<FreeboardData,String>>freeBoardList;
    public int point;

    public UserData(ArrayList<String> myTeamList, ArrayList<Pair<CommentData, String>> commentList, ArrayList<Pair<FreeboardData, String>> freeBoardList, int point) {
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
    public ArrayList<String> getMyTeamList() {
        return myTeamList;
    }

    public void setMyTeamList(ArrayList<String> myTeamList) {
        this.myTeamList = myTeamList;
    }

    public ArrayList<Pair<CommentData, String>> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Pair<CommentData, String>> commentList) {
        this.commentList = commentList;
    }

    public ArrayList<Pair<FreeboardData, String>> getFreeBoardList() {
        return freeBoardList;
    }

    public void setFreeBoardList(ArrayList<Pair<FreeboardData, String>> freeBoardList) {
        this.freeBoardList = freeBoardList;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
