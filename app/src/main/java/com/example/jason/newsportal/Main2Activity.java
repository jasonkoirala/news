package com.example.jason.newsportal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


public class Main2Activity extends AppCompatActivity implements EmployeeListFragment.EmployeeListFragmentInterface{

    private static final String TAG = Main2Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }




    @Override
    public void callEmployeeDetail(long employeeIndex) {
        Log.v(TAG, "In Main Activity got the following index "+employeeIndex);

        //Check if the FrameLayout is being used

        View frameLayoutView = findViewById(R.id.detailFragmentId);
        if(frameLayoutView != null){
            //Create the detail Fragment Object
            //Start Fragment Transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detailFragmentId, EmployeeDetailFragment.newInstance(employeeIndex));
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else{
            //Call Detail Activity
        }

    }
}