package com.example.jason.newsportal;

/**
 * Created by jason on 20/08/2017.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EmployeeListFragment extends ListFragment {

    private static final String TAG = EmployeeListFragment.class.getSimpleName();
    private EmployeeListFragmentInterface employeeListFragmentInterfaceRef;

    public EmployeeListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,
                Employee.getEmployeeNames());
        setListAdapter(employeeAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EmployeeListFragmentInterface) {
            employeeListFragmentInterfaceRef = (EmployeeListFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()+" must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        employeeListFragmentInterfaceRef = null;
    }

    public void onListItemClick(ListView listView, View view, int position, long id){
        Log.v(TAG, "Following List Item has been clicked position " + position + " and id " + id);
        if(employeeListFragmentInterfaceRef != null) {
            employeeListFragmentInterfaceRef.callEmployeeDetail(id);
        }
    }

    public interface EmployeeListFragmentInterface {
        void callEmployeeDetail(long employeeIndex);
    }
}