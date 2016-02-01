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
public class SecondCustomAdapter extends RecyclerView.Adapter<SecondCustomAdapter.ViewHolder> {
    private static final String TAG = "SecondCustomAdapter";

    private ArrayList<ListPerson> mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }

        public TextView getPersonName() {
            return personName;
        }

        public ImageView getPersonPhoto() {
            return personPhoto;
        }

        public TextView getPersonAge() {
            return personAge;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)


    public SecondCustomAdapter(ArrayList<ListPerson> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);

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
        viewHolder.getPersonAge().setText(mDataSet.get(position).getAge());
        viewHolder.getPersonName().setText(mDataSet.get(position).getName());
        viewHolder.getPersonPhoto().setImageResource(mDataSet.get(position).getPhotoId());
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}