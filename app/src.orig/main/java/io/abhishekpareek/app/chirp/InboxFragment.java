package io.abhishekpareek.app.chirp;

/**
 * Created by apareek on 1/27/16.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class InboxFragment extends ListFragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context mContext;
    private ArrayList<ListItem> listData;

    public InboxFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InboxFragment newInstance(Context c, int sectionNumber) {
        mContext = c;
        ButterKnife.bind((Activity) mContext);
        InboxFragment fragment = new InboxFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listData = getListData();
        CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), listData);

        setListAdapter(customListAdapter);
        getListView().setOnItemClickListener(this);

        /*
        mCustomListView.setAdapter(new CustomListAdapter(mContext, listData));
        mCustomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem newsData = (ListItem) mCustomListView.getItemAtPosition(position);
                Toast.makeText(mContext, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
            }
        });
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }


    private ArrayList<ListItem> getListData() {
        ArrayList<ListItem> listMockData = new ArrayList<ListItem>();
        String[] images = getResources().getStringArray(R.array.images_array);
        String[] headlines = getResources().getStringArray(R.array.headline_array);

        for (int i = 0; i < images.length; i++) {
            ListItem newsData = new ListItem();
            newsData.setUrl(images[i]);
            newsData.setHeadline(headlines[i]);
            newsData.setReporterName("Pankaj Gupta");
            newsData.setDate("May 26, 2013, 13:35");
            listMockData.add(newsData);
        }
        return listMockData;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

}