package com.dddg.project_dddg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        newsData.add(new NewsData("네이버 접속17","네이버 접속하기","https://www.naver.com/"));
        newsData.add(new NewsData("다음 접속16","다음 접속하기","https://www.daum.net/"));
        newsData.add(new NewsData("구글 접속15","구글 접속하기","https://www.google.co.kr/"));
        newsData.add(new NewsData("아마존 접속14","아마존 접속하기","https://www.amazon.com/"));
        newsData.add(new NewsData("인벤 접속13","인벤 접속하기","http://www.inven.co.kr/"));
        newsData.add(new NewsData("다음 접속12","다음 접속하기","https://www.daum.net/"));
        newsData.add(new NewsData("구글 접속11","구글 접속하기","https://www.google.co.kr/"));
        newsData.add(new NewsData("아마존 접속10","아마존 접속하기","https://www.amazon.com/"));
        newsData.add(new NewsData("인벤 접속9","인벤 접속하기","http://www.inven.co.kr/"));
        newsData.add(new NewsData("다음 접속8","다음 접속하기","https://www.daum.net/"));
        newsData.add(new NewsData("구글 접속7","구글 접속하기","https://www.google.co.kr/"));
        newsData.add(new NewsData("아마존 접속6","아마존 접속하기","https://www.amazon.com/"));
        newsData.add(new NewsData("인벤 접속5","인벤 접속하기","http://www.inven.co.kr/"));
        newsData.add(new NewsData("다음 접속4","다음 접속하기","https://www.daum.net/"));
        newsData.add(new NewsData("구글 접속3","구글 접속하기","https://www.google.co.kr/"));
        newsData.add(new NewsData("아마존 접속2","아마존 접속하기","https://www.amazon.com/"));
        newsData.add(new NewsData("인벤 접속1","인벤 접속하기","http://www.inven.co.kr/"));
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
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,true); //true는 최근것이 제일 위로
        layoutManager.setStackFromEnd(true);// recyclerview가 위에서 부터 체워진다
        recyclerView.setLayoutManager(layoutManager);
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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
