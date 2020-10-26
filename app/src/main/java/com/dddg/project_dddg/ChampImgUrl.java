package com.dddg.project_dddg;

import android.widget.Switch;

public class ChampImgUrl {
    public static String getUrl(String champ){
        switch (champ){
            case"챔프명": return "";
            default: return "https://firebasestorage.googleapis.com/v0/b/projectdddg.appspot.com/o/Team_img%2F(%EA%B5%AC)CJEntus.png?alt=media&token=7ce77c91-5b9d-489f-aa96-fbf28e6bc533";
        }

    }
}
