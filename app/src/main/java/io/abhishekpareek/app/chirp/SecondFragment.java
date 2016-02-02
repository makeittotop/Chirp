package io.abhishekpareek.app.chirp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SecondFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    };

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected EmptyRecyclerView mRecyclerView;
    protected SecondCustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;
    //private ArrayList<ListPerson> mListPersons;
    private ArrayList<ListUploadImage> mListUploadImages;

    public SecondFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SecondFragment newInstance(int sectionNumber) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String foo = getArguments().getString("edttext");
        Log.d(getActivity().getClass().getSimpleName(), "Second Fragment " + foo);

        View rootView = inflater.inflate(R.layout.fragment_second, container, false);


        mRecyclerView = (EmptyRecyclerView) rootView.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);

        TextView empTextView = (TextView) rootView.findViewById(R.id.emptyList);
        mRecyclerView.setEmptyView(empTextView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        //mListPersons = initializeData();
        mListUploadImages = initializeData();
        //listData = getListData();
        mAdapter = new SecondCustomAdapter(mListUploadImages);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private ArrayList<ListUploadImage> initializeData() {
        ArrayList<ListUploadImage> images = new ArrayList<ListUploadImage>();

        /*
        persons.add(new ListPerson("Emma Wilson", "23 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lavery Maiss", "25 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lillie Watts", "35 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Emma Wilson", "23 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lavery Maiss", "25 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lillie Watts", "35 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Emma Wilson", "23 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lavery Maiss", "25 years old", R.drawable.placeholder));
        persons.add(new ListPerson("Lillie Watts", "35 years old", R.drawable.placeholder));
        */

        return images;
    }

    protected void setUploadImagesData(ArrayList<ListUploadImage> images) {
        mListUploadImages.addAll(images);
        mAdapter.notifyDataSetChanged();
    }

    private void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState);
    }
}