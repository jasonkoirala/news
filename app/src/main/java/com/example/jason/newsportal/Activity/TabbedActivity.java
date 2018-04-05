package com.example.jason.newsportal.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jason.newsportal.Adapter.TabAdapter;
import com.example.jason.newsportal.Fragment.FragmentOne;
import com.example.jason.newsportal.R;

public class TabbedActivity extends AppCompatActivity {

    private static String news_CNN = "CNN";
    private static String news_Bloomberg = "Bloomberg";
    private static String news_BBC = "BBC";
    private static String news_BBCSport = "BBC Sport";
    private static String news_BusinessInsider = "Business Insider";
    private static String news_TechCrunch = "Tech Crunch";
    private static String news_MTV = "MTV Hot";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("World News");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        populateTab();

    }

    public void populateTab(){
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPagerMain = (ViewPager) findViewById(R.id.viewPagerMain);
        viewPagerMain.setOffscreenPageLimit(4);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentOne.newInstance("https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b", news_CNN),news_CNN);
        adapter.addFragment(FragmentOne.newInstance("https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b",news_Bloomberg),news_Bloomberg);
        adapter.addFragment(FragmentOne.newInstance("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b",news_BBC), news_BBC);
        adapter.addFragment(FragmentOne.newInstance("https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b", news_BBCSport), news_BBCSport);
        adapter.addFragment(FragmentOne.newInstance("https://newsapi.org/v1/articles?source=business-insider-uk&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b",news_BusinessInsider), news_BusinessInsider);
        adapter.addFragment(FragmentOne.newInstance(" https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b", news_TechCrunch), news_TechCrunch);
        adapter.addFragment(FragmentOne.newInstance(" https://newsapi.org/v1/articles?source=mtv-news&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b", news_MTV), news_MTV);
        viewPagerMain.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPagerMain);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
