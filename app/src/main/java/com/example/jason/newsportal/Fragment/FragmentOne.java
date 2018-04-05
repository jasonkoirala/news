package com.example.jason.newsportal.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jason.newsportal.Activity.NewsFeed;
import com.example.jason.newsportal.Adapter.RecylerSportsAdapter;
import com.example.jason.newsportal.Models.NewsModelDTO;
import com.example.jason.newsportal.R;
import com.example.jason.newsportal.Models.SportsNewsModel;
import com.example.jason.newsportal.listener.OnRecyclerviewItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class FragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerviewItemClickListener {
    RecyclerView mRecyclerView;
    private View view;
    private Context context;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<SportsNewsModel> dtoList;
    private String Server;

    public static FragmentOne newInstance(String url, String TabName) {

        Bundle args = new Bundle();
        args.putString("TabName", TabName);
        args.putString("url", url);
        FragmentOne fragment = new FragmentOne();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment_one, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        context = view.getContext();

        swipeLayout.setColorSchemeColors(ContextCompat.getColor(context, android.R.color.holo_green_dark),
                (ContextCompat.getColor(context, android.R.color.holo_blue_dark)),
                (ContextCompat.getColor(context, android.R.color.holo_red_dark)),
                (ContextCompat.getColor(context, android.R.color.holo_orange_dark)));


        return view;


    }


    private void populatesportsAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
        RecylerSportsAdapter adapter = new RecylerSportsAdapter(context, dtoList, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFragmentOne);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Server = getArguments().getString("url");
        GetSportsFromServer getSportsFromServer = new GetSportsFromServer();
        getSportsFromServer.execute();

    }

    @Override
    public void onRefresh() {
        GetSportsFromServer getSportsFromServer = new GetSportsFromServer();
        getSportsFromServer.execute();
        swipeLayout.setRefreshing(true);

    }

    @Override
    public void onItemClicked(int position) {

        SportsNewsModel sportsNewsModel = dtoList.get(position);
        Intent in = new Intent(getActivity(), NewsFeed.class);
        Bundle b = new Bundle();
        b.putString("NewsTitle", sportsNewsModel.getSportsNewsHeading());
        b.putString("NewsSource", sportsNewsModel.getSportsNewsSource());
        b.putString("NewsDescription", sportsNewsModel.getSportNewsDescription());
        b.putString("NewsImage", sportsNewsModel.getNewsSportsImageURL());
        b.putString("NewsURL", sportsNewsModel.getSportsNewsURL());
        b.putString("TabName", getArguments().getString("TabName"));
        in.putExtras(b);
        startActivity(in);

    }


    public class GetSportsFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                //final String Server = "https://newsapi.org/v1/articles?source=business-insider&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b";
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
                StringBuffer buffer = new StringBuffer();
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
                    JSONObject jsonObject = new JSONObject(jsonValinString);
                    Log.e("LOG_TAG", "String converted to JSON object");
                    System.out.println("Json String ::" + jsonObject);
                    dtoList = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        SportsNewsModel newsSportsModelDTO = new SportsNewsModel();
                        newsSportsModelDTO.setSportsNewsHeading(object.getString("title"));
                        if (TextUtils.isEmpty(object.optString("author"))) {
                            newsSportsModelDTO.setSportsNewsSource("");
                        } else {
                            newsSportsModelDTO.setSportsNewsSource(object.getString("author"));

                        }
                        newsSportsModelDTO.setSportNewsDescription(object.getString("description"));
                        newsSportsModelDTO.setNewsSportsImageURL(object.getString("urlToImage"));
                        newsSportsModelDTO.setSportsNewsURL(object.getString("url"));
//                        newsSportsModelDTO.setArticleURL(object.getString("url"));
                        //newsModelDTO.setDate(object.getString("date"));
                        dtoList.add(newsSportsModelDTO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                Toast.makeText(context, "Error:" + e, Toast.LENGTH_LONG).show();
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
                    }
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeLayout.setRefreshing(false);
            populatesportsAdapter();
        }
    }


}
