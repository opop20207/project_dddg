package com.dddg.project_dddg;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchPlanData {

    public String date;
    public String matchtime;
    public String title;
    public String team1;
    public String team2;
    public int team1_vote;
    public int team2_vote;
    public ArrayList<String> voteListName;
    public ArrayList<Integer> voteListNum;
    public MatchPlanData(){
        date = new String();
        matchtime = new String();
        title = new String();
        team1 = new String();
        team2 = new String();
        team1_vote = 0;
        team2_vote = 0;
        voteListName = new ArrayList<String>();
        voteListNum = new ArrayList<Integer>();
    }

    public MatchPlanData(String date, String matchtime, String title, String team1, String team2, int team1_vote, int team2_vote, ArrayList<String> voteListName, ArrayList<Integer> voteListNum) {
        this.date = date;
        this.matchtime = matchtime;
        this.title = title;
        this.team1 = team1;
        this.team2 = team2;
        this.team1_vote = team1_vote;
        this.team2_vote = team2_vote;
        this.voteListName = voteListName;
        this.voteListNum = voteListNum;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("date",date);
        map.put("title",title);
        map.put("matchtime",matchtime);
        map.put("team1",team1);
        map.put("team2",team2);
        map.put("team1_vote",team1_vote);
        map.put("team2_vote",team2_vote);
        map.put("voteListName",voteListName);
        map.put("voteListNum",voteListNum);
        return map;
    }

    public String getMatchtime() {
        return matchtime;
    }

    public void setMatchtime(String matchtime) {
        this.matchtime = matchtime;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public int getTeam1_vote() {
        return team1_vote;
    }

    public void setTeam1_vote(int team1_vote) {
        this.team1_vote = team1_vote;
    }

    public int getTeam2_vote() {
        return team2_vote;
    }

    public void setTeam2_vote(int team2_vote) {
        this.team2_vote = team2_vote;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
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

}
