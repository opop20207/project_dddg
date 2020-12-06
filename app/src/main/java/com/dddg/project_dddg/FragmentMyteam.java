package com.dddg.project_dddg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FragmentMyteam extends Fragment {
    ArrayList<TeamData> teamData = new ArrayList<TeamData>(Arrays.asList(new TeamData()));
    static FragmentMyteam instance;
    public FragmentMyteam() {
    }

    public static FragmentMyteam getInstance(){
        if(instance==null){
            instance = new FragmentMyteam();
        }
        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_myteam,container,false);
        Button button = (Button) rootview.findViewById(R.id.btn_myteam);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final CharSequence[] teamName = {"1","2","3","4","5","6","7","8"};
                final int[] hold = new int[1];

                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                dlg.setTitle("팀 선택").setSingleChoiceItems(teamName, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hold[0] = i;
                    }
                }).setNeutralButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i>=0) Toast.makeText(getContext(), teamName[hold[0]], Toast.LENGTH_LONG).show();
                    }
                }).setCancelable(false).show();
            }
        });
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
