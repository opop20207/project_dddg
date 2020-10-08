package com.dddg.project_dddg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentNews extends Fragment {
    static FragmentNews instance;
    private FragmentNews() {

    }
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    NewsRVAdapter adapter;
    ArrayList<NewsData> newsData = new ArrayList<NewsData>(Arrays.asList(new NewsData()));

    public static FragmentNews getInstance(){
        if(instance==null){
            instance = new FragmentNews();
        }
        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 임시 recyclerview 테스트를 위한 NewsData세팅
        // 나중에 들어가는게 제일 상단 배치
        newsData.clear();
        //필요한작업
        //NewsData를 크롤링으로 받아서 세팅 1.뉴스제목  2.뉴스내용  3.뉴스 링크
        //SwipeRefreshLayout를 이용하면 NewsData 새로고침 업데이트 가능
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
        recyclerView = getView().findViewById(R.id.news_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false); //true는 최근것이 제일 위로
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new NewsRVAdapter(newsData);
        adapter.setOnItemClickListener(new NewsRVAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                // 뉴스창 클릭했을때
                Intent webintent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsData.get(position).news_Url));
                startActivity(webintent);
                // 뉴스 웹주소로 연결
            }
        });
        recyclerView.setAdapter(adapter);
        new Thread(){
            @Override
            public void run() {
                String  NewsUrl = "http://www.inven.co.kr/webzine/news/?page=1";
                Document doc = null;
                try {
                    doc = Jsoup.connect(NewsUrl).get();
                    Elements newsList = doc.select("div.webzineNewsList.tableType2 tr");
                    for(Element list: newsList){
                        newsData.add(new NewsData( list.select("span.title a").text(),
                                list.select("span.summary").text(),
                                list.select("img.banner").attr("src"),
                                list.select("span.title a").attr("href")));
                        Log.d("로그",list.select("img.banner").attr("src"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    FragmentActivity fragmentActivity = getActivity();
                    if(fragmentActivity != null)
                    fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }

        }.start();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
