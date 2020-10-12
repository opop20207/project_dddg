package com.dddg.project_dddg;

public class TeamImgUrl {
    String team_name;
    public static String Url(String team_name){
        String returnUrl = "";
        if(team_name==null) return returnUrl;
        switch (team_name){
            case "T1": returnUrl = "asdfasdf";
                break;
            default:

        }
        return returnUrl;
    }
    public TeamImgUrl(String team_name) {
        this.team_name = team_name;
    }
}
