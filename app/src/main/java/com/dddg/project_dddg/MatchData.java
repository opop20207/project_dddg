package com.dddg.project_dddg;

import java.io.Serializable;

public class MatchData implements Serializable {
    public String date;
    public String title;
    public String stage;
    public String  winteam;
    public String  gamescore;
    public String  gametime;
    public String  team1_name;
    public String  team2_name;
    public String  team1_teamscore;
    public String  team2_teamscore;
    public String  team1_playername;
    public String  team2_playername;
    public String  team1_playerscore;
    public String  team2_playerscore;
    public String  team1_champ;
    public String  team2_champ;
    public String  team1_ban;
    public String  team2_ban;
    public MatchData(){

    }
    public MatchData(String date, String title, String stage, String winteam, String gamescore, String gametime, String team1_name, String team2_name, String team1_teamscore, String team2_teamscore, String team1_playername, String team2_playername, String team1_playerscore, String team2_playerscore, String team1_champ, String team2_champ, String team1_ban, String team2_ban) {
        this.date = date;
        this.title = title;
        this.stage = stage;
        this.winteam = winteam;
        this.gamescore = gamescore;
        this.gametime = gametime;
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.team1_teamscore = team1_teamscore;
        this.team2_teamscore = team2_teamscore;
        this.team1_playername = team1_playername;
        this.team2_playername = team2_playername;
        this.team1_playerscore = team1_playerscore;
        this.team2_playerscore = team2_playerscore;
        this.team1_champ = team1_champ;
        this.team2_champ = team2_champ;
        this.team1_ban = team1_ban;
        this.team2_ban = team2_ban;
    }
    public String getKey(){
        return title+stage+winteam;

    }
}
