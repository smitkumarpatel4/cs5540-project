package com.munachimsoani.android.personalfeedapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.munachimsoani.android.personalfeedapp.model.News;
import com.munachimsoani.android.personalfeedapp.utilities.JSONUtils;
import com.munachimsoani.android.personalfeedapp.utilities.NetworkUtils;
import com.munachimsoani.android.personalfeedapp.utilities.NewsAdapter;
import com.munachimsoani.android.personalfeedapp.utilities.SimpleDividerItemDecoration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;

    private TextView mDispalyName;
    private TextView mUserEmail;

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private ArrayList<News> news = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = findViewById(R.id.drawerLayout);

        mToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(mToolbar);


        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Add the logged in user to the navigation view

        @SuppressLint("WrongViewCast") NavigationView navigationView = findViewById(R.id.navigation_view);
//        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);


        mDispalyName = navHeaderView.findViewById(R.id.username);
        mUserEmail = navHeaderView.findViewById(R.id.user_email);


        //     Get user from firebase and display
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address
            String name = user.getDisplayName();
            String email = user.getEmail();

            mDispalyName.setText(name);
            mUserEmail.setText(email);

        }



        mRecyclerView = findViewById(R.id.my_recycler_view);
        mAdapter = new NewsAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));



        makeNetworkQueryTopStories();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.nav_settings:


                }
                return false;
            }
        });


    }


    public void makeNetworkQueryTopStories(){
        URL networkURL = NetworkUtils.buildUrlTopStories();
        Log.d("test",networkURL.toString());
        // mSearchResultsTextView.setText(networkURL.toString());
        new NewsTask().execute(networkURL);
    }

    public class NewsTask extends AsyncTask<URL,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsResults = null;
            try {
                newsResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsResults;
        }


        @Override
        protected void onPostExecute(String newsResult) {
            super.onPostExecute(newsResult);
            news = JSONUtils.makeNewsListTopStories(newsResult);
            mAdapter.mNews.addAll(news);
            mAdapter.notifyDataSetChanged();


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);
    }
}
