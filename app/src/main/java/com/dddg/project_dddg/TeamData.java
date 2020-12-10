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

    public ArrayList<Player_info> position;

    public TeamData() {
        this.teamname = new String();
        this.coach = new String();
        this.director = new String();
        this.player = new String();
        position = new ArrayList<>();
    }

    public TeamData(String teamname, String coach, String director, String player) {
        this.teamname = teamname;
        this.coach = coach;
        this.director = director;
        this.player = player;
        position = new ArrayList<>();
        matching();
    }
    public void matching(){
        position.clear();
        ArrayList<String> split = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();
        if(!player.equals("")) {
            split.addAll(Arrays.asList(player.split("/")));
            for(int i =0;i<split.size();i++){
                tmp.clear();
                tmp.addAll(Arrays.asList(split.get(i).split("#")));
                for(int j =0;j<tmp.size();j++)
                position.add(new Player_info(tmp.get(0),tmp.get(1),tmp.get(2)));
            }
        }
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
class Player_info{
    public String name;
    public String nickname;
    public String position;

    public Player_info(String name, String nickname, String position) {
        this.name = name;
        this.nickname = nickname;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}