package com.example.jason.newsportal.Activity;

import android.support.v7.app.ActionBarDrawerToggle;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.newsportal.Adapter.RecyclerAdapter;
import com.example.jason.newsportal.Models.NewsModelDTO;
import com.example.jason.newsportal.R;
import com.example.jason.newsportal.Util.AppUtil;
import com.example.jason.newsportal.WebView.NewViewWebsite;
import com.example.jason.newsportal.databaseHandler.DatabaseHandler;
import com.example.jason.newsportal.listener.OnRecyclerviewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.jason.newsportal.R.id.toolbar;

public class MainActivity extends AppCompatActivity implements OnRecyclerviewItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerViewMain;
    private ArrayList<NewsModelDTO> dtoList;
    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;
    DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperMainActivity);
        swipeLayout.setOnRefreshListener(this);

        swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, android.R.color.holo_green_dark),
                (ContextCompat.getColor(this, android.R.color.holo_blue_dark)),
                (ContextCompat.getColor(this, android.R.color.holo_red_dark)),
                (ContextCompat.getColor(this, android.R.color.holo_orange_dark)));

        setNavigationDrawer();
        startConnectionToServer();


    }

    private void setNavigationDrawer() {

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View

// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // create a Fragment Object
                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.cnn) {
                    Toast.makeText(getApplicationContext(), "CNN clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.bloomberg) {
                    Toast.makeText(getApplicationContext(), "Bloomberg clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.bbc) {
                    Toast.makeText(getApplicationContext(), "BBC clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.bbc_sports) {
                    Toast.makeText(getApplicationContext(), "BBC sports clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.business_insider) {
                    Toast.makeText(getApplicationContext(), "Business Insider clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.tech_crunch) {
                    Toast.makeText(getApplicationContext(), "Tech Crunch clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                } else if (itemId == R.id.mtv_hot) {
                    Toast.makeText(getApplicationContext(), "MTV clicked", Toast.LENGTH_LONG).show();
                    dLayout.closeDrawers();
                }
// display a toast message with menu item's title
                return false;
            }
        });
        navView.setItemIconTintList(null);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        dLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    private void startConnectionToServer() {

        if (AppUtil.isNetworkAvailable(this)) {
            swipeLayout.setRefreshing(true);
            GetFromServer getFromServer = new GetFromServer();
            getFromServer.execute();
        } else {
            swipeLayout.setRefreshing(false);
            Toast.makeText(getApplicationContext(), "No network connection.", Toast.LENGTH_LONG).show();
            loadOldDataFromDatabase();
            //loadOldData();

        }

    }

    private void loadOldDataFromDatabase() {

        DatabaseHandler dbh = new DatabaseHandler(this);
        dtoList = (ArrayList<NewsModelDTO>) dbh.getNewsFromDatabase();
        populateRecycler();

    }

    private void loadOldData() {

        SharedPreferences mSharedPreferences = getSharedPreferences("My_Data", MODE_PRIVATE);

        try {
            JSONArray array = new JSONArray(mSharedPreferences.getString("someValue", ""));
            dtoList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                NewsModelDTO newsModelDTO = new NewsModelDTO();
                newsModelDTO.setTitle(object.getString("title"));
                newsModelDTO.setSource(object.getString("author"));
                newsModelDTO.setDescription(object.getString("description"));
                newsModelDTO.setImageURL(object.getString("urlToImage"));
                newsModelDTO.setArticleURL(object.getString("url"));
                //newsModelDTO.setDate(object.getString("date"));
                dtoList.add(newsModelDTO);
            }


            populateRecycler();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.word_news:
                if (AppUtil.isNetworkAvailable(this) == true) {
                    Intent intent = new Intent(MainActivity.this, TabbedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection.", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void populateRecycler() {
        if (dtoList != null && dtoList.size() > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager manager = new GridLayoutManager(this, 1);
            RecyclerAdapter adapter = new RecyclerAdapter(this, dtoList, this);
            recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMain);
            recyclerViewMain.setLayoutManager(manager);
            recyclerViewMain.setAdapter(adapter);
        } else {

            Toast.makeText(getApplicationContext(), "Network connection not working.", Toast.LENGTH_LONG).show();
            loadOldDataFromDatabase();
            swipeLayout.setRefreshing(false);

        }

    }

    @Override
    public void onItemClicked(int position) {

        Intent in = new Intent(this, NewsFeed.class);
        NewsModelDTO newsModelDTO = dtoList.get(position);

        Bundle b = new Bundle();
        b.putString("NewsTitle", newsModelDTO.getTitle());
        b.putString("NewsSource", newsModelDTO.getSource());
        b.putString("NewsDescription", newsModelDTO.getDescription());
        b.putString("NewsImage", newsModelDTO.getImageURL());
        b.putString("NewsURL", newsModelDTO.getArticleURL());
        in.putExtras(b);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(this,
                    R.anim.bottom_in, R.anim.top_out).toBundle();
            startActivity(in, bundleAnimation);
        } else {
            startActivity(in);
        }
    }

    @Override
    public void onRefresh() {

        startConnectionToServer();

    }


    private class GetFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                final String Server = "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b";
                Uri builtUri = Uri.parse(Server)
                        .buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
//                urlConnection .setRequestProperty ("Authorization","Basic YWRtaW46YWRtaW4=");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                //Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {

                    //Nothing to do
                    System.out.println("input stream null");
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {

                    //Since it's JSON, adding a newline isn't necessary(it won't affect parsing)
                    //but it does make debugging a lot easier if you print out the completed
                    //buffer for debugging.

                    buffer.append(line + "\n");
                }
                System.out.println("buffer length:: " + buffer.length());
                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing.
                    return null;
                }

                String jsonValinString = buffer.toString();
                try {

                    if (AppUtil.ifDatabaseHasValue(MainActivity.this)) {
                        DatabaseHandler dbh = new DatabaseHandler(MainActivity.this);
                        dbh.deleteOldNewsFromDatabase();

                    }
                    JSONObject jsonObject = new JSONObject(jsonValinString);
                    Log.e("LOG_TAG", "String converted to JSON object");
                    System.out.println("Json String ::" + jsonObject);
                    //Call SharedPreferences to store Loaded JSON data;
                    //saveData(jsonObject);
                    saveDataToDatabase(jsonObject);
                    dtoList = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        NewsModelDTO newsModelDTO = new NewsModelDTO();
                        newsModelDTO.setTitle(object.getString("title"));
                        newsModelDTO.setSource(object.getString("author"));
                        newsModelDTO.setDescription(object.getString("description"));
                        newsModelDTO.setImageURL(object.getString("urlToImage"));
                        newsModelDTO.setArticleURL(object.getString("url"));
                        //newsModelDTO.setDate(object.getString("date"));
                        dtoList.add(newsModelDTO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                Toast.makeText(MainActivity.this, "Error:" + e, Toast.LENGTH_LONG).show();
                e.printStackTrace();


            } catch (IOException e) {
                System.out.println("error:: " + e.getMessage());
                e.printStackTrace();
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();

                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            populateRecycler();
            swipeLayout.setRefreshing(false);
        }
    }

    private void stopLoading() {
        swipeLayout.setRefreshing(false);
    }

    private void startLoading() {
        swipeLayout.setRefreshing(true);
    }

    private void saveDataToDatabase(JSONObject jsonObject) {

        DatabaseHandler dbh = new DatabaseHandler(this);
        dbh.addNewsToDatabase(jsonObject);

    }

    private void saveData(JSONObject jsonObject) {

        try {
            SharedPreferences mSharedPreferences;
            SharedPreferences.Editor mEditor;

            mSharedPreferences = getSharedPreferences("My_Data", MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
            JSONArray array = jsonObject.getJSONArray("articles");
            mEditor.putString("someValue", array.toString());
            mEditor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
