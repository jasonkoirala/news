package com.example.jason.newsportal.Util;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.jason.newsportal.databaseHandler.DatabaseHandler;

/**
 * Created by jason on 10/08/2017.
 */

public class AppUtil {
    public static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        return Math.round(size.x / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return size.x;

    }


//    public int dpToPx(int dp) {
//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//    }


    public static boolean ifDatabaseHasValue(Context context) {

        DatabaseHandler dbh = new DatabaseHandler(context);
        int newsNumber = dbh.getNewsCount();
        if(newsNumber > 0) {
            return true;
        }
        else {
            return false;
        }

    }


    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();


    }
}
