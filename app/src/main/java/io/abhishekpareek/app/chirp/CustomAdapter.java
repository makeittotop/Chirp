package io.abhishekpareek.app.chirp;

/**
 * Created by apareek on 2/1/16.
 */


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private ArrayList<ListItem> mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            //textView = (TextView) v.findViewById(R.id.textView);
            headlineView = (TextView) v.findViewById(R.id.title);
            reportedDateView = (TextView) v.findViewById(R.id.date);
            reporterNameView = (TextView) v.findViewById(R.id.reporter);
            imageView = (ImageView) v.findViewById(R.id.thumbImage);
        }

        /*
        public TextView getTextView() {
            return textView;
        }
        */

        public TextView getHeadlineView() {
            return headlineView;
        }

        public TextView getReporterNameView() {
            return reporterNameView;
        }

        public TextView getReportedDateView() {
            return reportedDateView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)


    public CustomAdapter(ArrayList<ListItem> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row_layout, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        //viewHolder.getTextView().setText(mDataSet[position]);
        viewHolder.getHeadlineView().setText(mDataSet.get(position).getHeadline());
        viewHolder.getReportedDateView().setText(mDataSet.get(position).getDate());
        viewHolder.getReporterNameView().setText(mDataSet.get(position).getReporterName());
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}