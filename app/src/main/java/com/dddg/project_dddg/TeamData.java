package com.dddg.project_dddg;

import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TeamData {
    public String teamname;
    public String coach;
    public String director;
    public String player;
    // firebase 로딩용


    public TeamData() {
        this.teamname = new String();
        this.coach = new String();
        this.director = new String();
        this.player = new String();
    }

    public TeamData(String teamname, String coach, String director, String player) {
        this.teamname = teamname;
        this.coach = coach;
        this.director = director;
        this.player = player;
    }
    public ArrayList<PlayerInfo> matching(){
        ArrayList<PlayerInfo> position = new ArrayList<>();
        position.clear();
        ArrayList<String> split = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();
        if(!player.equals("")) {
            split.addAll(Arrays.asList(player.split("/")));
            for(int i =0;i<split.size();i++){
                tmp.clear();
                tmp.addAll(Arrays.asList(split.get(i).split("#")));
                position.add(new PlayerInfo(tmp.get(0),tmp.get(1),tmp.get(2)));
            }
        }
        return position;
    }
    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

}