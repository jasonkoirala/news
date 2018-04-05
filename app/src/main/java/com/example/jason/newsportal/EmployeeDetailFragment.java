package com.example.jason.newsportal; /**
 * Created by jason on 20/08/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.jason.newsportal.R;

public class EmployeeDetailFragment extends Fragment implements View.OnClickListener{

    long employeeIndex;

    private static String keyEmployeeIndex = "keyEmployeeIndex";

    public static Fragment newInstance(long employeeIndex){
        EmployeeDetailFragment fragment = new EmployeeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(keyEmployeeIndex,employeeIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            employeeIndex = getArguments().getLong(keyEmployeeIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        Button testButton = (Button) layout.findViewById(R.id.testButtonId);

        testButton.setOnClickListener(this);

        //Handle the Child Fragment. For demo purpose I haven't created a new Fragment
        FragmentTransaction ft = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ft = getChildFragmentManager().beginTransaction();
        }
        EmployeeListFragment elf = new EmployeeListFragment();
        ft.replace(R.id.employeeAddressFragmentId, elf);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
        //End

        return layout;
    }

    public void setEmployeeIndex(long employeeIndex) {
        this.employeeIndex = employeeIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("employeeIndex", employeeIndex);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.testButtonId:
                //Do something
                break;
        }
    }
}