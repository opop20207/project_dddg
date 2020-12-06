package com.dddg.project_dddg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.adapter.NewsRVAdapter;
import com.facebook.ProfileTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentNews extends Fragment {
    static FragmentNews instance;
    public FragmentNews() {

    }
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager_list;
    GridLayoutManager layoutManager_grid;
    NewsRVAdapter adapter_list;
    NewsRVAdapter adapter_grid;
    ArrayList<NewsData> newsData = new ArrayList<NewsData>(Arrays.asList(new NewsData()));
    FloatingActionButton next;
    FloatingActionButton prev;
    TextView pagenum;
    int MaxPage = 1; //최대 페이지
    int webpage_num = 1;// 현재 페이지
    int recyclerview_mode = 0;
    ImageButton layout_mode_btn;
    public static FragmentNews getInstance(){
        if(instance==null){
            instance = new FragmentNews();
        }
        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsData.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View v =inflater.inflate(R.layout.fragment_news,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout_mode_btn = getView().findViewById(R.id.news_layout_mode_button);
        next = getView().findViewById(R.id.next_page_button);
        prev = getView().findViewById(R.id.previous_page_button);
        pagenum = getView().findViewById(R.id.news_pagenum);
        recyclerView = getView().findViewById(R.id.news_recyclerview);
        layoutManager_list = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false); //true는 최근것이 제일 위로
        layoutManager_grid = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager_list);
        recyclerView.setHasFixedSize(true);
        adapter_list = new NewsRVAdapter(newsData,0);
        adapter_list.setOnItemClickListener(new NewsRVAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                // 뉴스창 클릭했을때
                Intent webintent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsData.get(position).news_Url));
                startActivity(webintent);
                // 뉴스 웹주소로 연결
            }
        });
        adapter_grid = new NewsRVAdapter(newsData,1);
        adapter_grid.setOnItemClickListener(new NewsRVAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                // 뉴스창 클릭했을때
                Intent webintent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsData.get(position).news_Url));
                startActivity(webintent);
                // 뉴스 웹주소로 연결
            }
        });
        recyclerView.setAdapter(adapter_list);
        Loaddata loadData = new Loaddata();
        loadData.start();
        RotateAnimation rotate = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(500);

        layout_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerview_mode == 0){
                    recyclerview_mode = 1;
                    recyclerView.setAdapter(adapter_grid);
                    recyclerView.setLayoutManager(layoutManager_grid);
                    Glide.with(getActivity()).load(R.drawable.ic_list_mode).into(layout_mode_btn);
                    layout_mode_btn.startAnimation(rotate);
                    adapter_grid.notifyDataSetChanged();
                }
               else {
                    recyclerview_mode = 0;
                    recyclerView.setAdapter(adapter_list);
                    recyclerView.setLayoutManager(layoutManager_list);
                    Glide.with(getActivity()).load(R.drawable.ic_grid_mode).into(layout_mode_btn);
                    layout_mode_btn.startAnimation(rotate);
                    adapter_list.notifyDataSetChanged();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() { //다음칸
            @Override
            public void onClick(View v) {
                webpage_num++;
                Loaddata loadData = new Loaddata();
                loadData.start();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() { //이전칸
            @Override
            public void onClick(View v) {
                webpage_num--;
                prev.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                Loaddata loadData = new Loaddata();
                loadData.start();
            }
        });
    }
    public class Loaddata extends Thread{
        Loaddata(){

        }
        @Override
        public void run() {
            super.run();
            String  NewsUrl = "http://www.inven.co.kr/webzine/news/?sclass=31&iskin=esports&page="+ Integer.toString(webpage_num);
            Document doc = null;
            try {
                doc = Jsoup.connect(NewsUrl).get();
                MaxPage = Integer.parseInt(doc.select("span.basetext > a:last-of-type").text().toString());
                // 최대 페이지 추출
                Elements newsList = doc.select("div.webzineNewsList.tableType2 tr");
                // 현재 화면 뉴스 리스트 추출
                newsData.clear();
                for(Element list: newsList){  //뉴스 item 갱신부분
                    newsData.add(new NewsData( list.select("span.title a").text(),
                            list.select("span.summary").text(),
                            list.select("img.banner").attr("src"),
                            list.select("span.title a").attr("href"),
                            list.select("span.info").text()));
                }

                FragmentActivity fragmentActivity  = getActivity();
                if(fragmentActivity != null)
                    fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 새로 고침 목록
                            Log.d("로그","adapter.notifyDataSetChanged() 호출");
                            if(recyclerview_mode == 0)adapter_list.notifyDataSetChanged(); //recyclerview 갱신
                            else adapter_grid.notifyDataSetChanged(); //recyclerview 갱신
                            if(webpage_num <= 1) prev.setVisibility(View.GONE);
                            else prev.setVisibility(View.VISIBLE);
                            if(webpage_num >= MaxPage) next.setVisibility(View.GONE);
                            else next.setVisibility(View.VISIBLE);
                            pagenum.setText(Integer.toString(webpage_num)+"/"+Integer.toString(MaxPage));
                            // 버튼 및 페이지 넘버링
                        }
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
