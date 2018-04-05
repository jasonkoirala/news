package com.example.jason.newsportal.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.newsportal.Models.NewsModelDTO;
import com.example.jason.newsportal.R;
import com.example.jason.newsportal.WebView.NewViewWebsite;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity {
    //    private NewsModelDTO mNewsModelDTO;
    private ArrayList<NewsModelDTO> dtoList;
    String newsTitle, newsSource, newsDesc, newsImageURL, newsURL, TabName;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Bundle extras = getIntent().getExtras();
        newsTitle = extras.getString("NewsTitle");
        newsSource = extras.getString("NewsSource");
        newsDesc = extras.getString("NewsDescription");
        newsImageURL = extras.getString("NewsImage");
        newsURL = extras.getString("NewsURL");
        TabName = extras.getString("TabName");
        System.out.println("some dummy changes");
        System.out.println("Again some changes");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        System.out.println("NewsSource ==" + newsSource);
        if (TextUtils.isEmpty(newsSource) || newsSource.length() > 25) {
            toolbar.setTitle(TabName);
        } else {
            toolbar.setTitle(newsSource.toString());
        }
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        Bundle extras = getIntent().getExtras();
//        newsTitle = extras.getString("NewsTitle");
//        newsSource = extras.getString("NewsSource");
//        newsDesc = extras.getString("NewsDescription");
//        newsImageURL = extras.getString("NewsImage");
//        newsURL = extras.getString("NewsURL");
//        GetFromServer getFromServer = new GetFromServer();
//        getFromServer.execute();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        displayImageOptions = new DisplayImageOptions.Builder().build();
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.appbar_collapse);
//        collapsingToolbarLayout.setTitle("News Feed");
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        populateCollapsibleLayout();

    }

    public void openWeb(View v) {

        Intent intent = new Intent(this, NewViewWebsite.class);
        Bundle bundle = new Bundle();
        bundle.putString("newsURL", newsURL);
        intent.putExtras(bundle);
        startActivity(intent);

    }


//    public class GetFromServer extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            try {
//                final String Server = "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=d9c12224e4914df788882e1a3bbc628b";
//                Uri builtUri = Uri.parse(Server)
//                        .buildUpon()
//                        .build();
//
//                URL url = new URL(builtUri.toString());
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
////                urlConnection .setRequestProperty ("Authorization","Basic YWRtaW46YWRtaW4=");
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("Accept", "application/json");
//                urlConnection.connect();
//
//                //Read the input stream into a string
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//
//                    //Nothing to do
//                    System.out.println("input stream null");
//                    return null;
//                }
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//
//                    //Since it's JSON, adding a newline isn't necessary(it won't affect parsing)
//                    //but it does make debugging a lot easier if you print out the completed
//                    //buffer for debugging.
//
//                    buffer.append(line + "\n");
//                }
//                System.out.println("buffer length:: " + buffer.length());
//                if (buffer.length() == 0) {
//                    //Stream was empty. No point in parsing.
//                    return null;
//                }
//
//                String jsonValinString = buffer.toString();
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonValinString);
//                    Log.e("LOG_TAG", "String converted to JSON object");
//                    System.out.println("Json String ::" + jsonObject);
//                    dtoList = new ArrayList<>();
//                    JSONArray array = jsonObject.getJSONArray("articles");
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.getJSONObject(i);
//                        NewsModelDTO newsModelDTO = new NewsModelDTO();
//                        newsModelDTO.setTitle(object.getString("title"));
//                        newsModelDTO.setSource(object.getString("author"));
//                        newsModelDTO.setDescription(object.getString("description"));
//                        newsModelDTO.setImageURL(object.getString("urlToImage"));
////                        newsModelDTO.setArticleURL(object.getString("url"));
////                        newsModelDTO.setDate(object.getString("date"));
//                        dtoList.add(newsModelDTO);
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            } catch (MalformedURLException e) {
//                //Toast.makeText(this, "Error:" + e, Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//
//
//            } catch (IOException e) {
//                System.out.println("error:: " + e.getMessage());
//                e.printStackTrace();
//            } finally {
//
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//
//                    } catch (final IOException e) {
//                    }
//                }
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            populateCollapsibleLayout(dtoList);
//        }
//    }

    private void populateCollapsibleLayout(/*List<NewsModelDTO> newsModelDTOList*/) {
        NewsModelDTO mNewsModelDTO;
        TextView collapseNews_title, collapserNews_date, collapseNews_desc, collapseNews_source;
        ImageView collapsibleImageView;
        collapsibleImageView = (ImageView) findViewById(R.id.collapsibleImageView);
        collapseNews_title = (TextView) findViewById(R.id.collapseNews_title);
        collapseNews_desc = (TextView) findViewById(R.id.collapseNews_desc);
        collapseNews_source = (TextView) findViewById(R.id.collapseNews_source);
//        for(int i = 0; i < dtoList.size(); i++) {
//        mNewsModelDTO = newsModelDTOList.get(i);
        imageLoader.displayImage(newsImageURL, (ImageView) findViewById(R.id.collapsibleImageView), displayImageOptions);
        collapseNews_title.setText(newsTitle);
//            collapserNews_date.setText(mNewsModelDTO.getDate());
        collapseNews_source.setText(newsSource);
        collapseNews_desc.setText(newsDesc);
//        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.bottm_down, R.anim.top_down);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    Intent in = new Intent(this, MainActivity.class);
//                    Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(this,
//                            R.anim.bottom_in, R.anim.top_out).toBundle();
//                    startActivity(in, bundleAnimation);
//                } else {
//                    finish();
//                }
                finish();
                overridePendingTransition(R.anim.bottm_down, R.anim.top_down);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}