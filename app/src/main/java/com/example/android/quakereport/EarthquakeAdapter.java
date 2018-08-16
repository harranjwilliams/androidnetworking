package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    public static final String LOG_TAG = EarthquakeAdapter.class.getName();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    private List<Earthquake> mEarthquakes;

    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for multiple TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        //super(context, 0, earthquakes);

        mEarthquakes = earthquakes;
        Log.i(LOG_TAG, "Earthquakes list size = " + mEarthquakes.size());

    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder{

        public final TextView mMagnitudeView;
        public final TextView mLocationView;
        public final TextView mDateView;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);

            mMagnitudeView = itemView.findViewById(R.id.magnitude);
            mLocationView = itemView.findViewById(R.id.location);
            mDateView = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        boolean attachToRoot = false;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.list_item, parent, attachToRoot);
        EarthquakeViewHolder viewHolder = new EarthquakeViewHolder(view);

        Log.i(LOG_TAG, "View holder created");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {

        Earthquake earthquake = mEarthquakes.get(position);

        String magnitude = Double.toString(earthquake.getMagnitude());
        String location = earthquake.getLocation();
        String date = dateFormat.format(new Date(earthquake.getDate()));

        Log.i(LOG_TAG, "Position = " + position + " Magnitude = " + magnitude + " Location = " + location + " Date = " + date);

        holder.mMagnitudeView.setText(magnitude);
        holder.mLocationView.setText(location);
        holder.mDateView.setText(date);
    }

    @Override
    public int getItemCount() {
        if (mEarthquakes == null) {
            return 0;
        }
        return mEarthquakes.size();
    }

    public void setEarthQuakeData(List<Earthquake> earthquakes) {
        mEarthquakes = earthquakes;
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View listItemView = convertView;
//
//        // Check if the existing view is being reused, otherwise inflate the view
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
//        }
//
//        // Get the {@link Earthquake} object located at this position in the list
//        Earthquake earthquake = getItem(position);
//
//        // Find the TextView in the list_item.xml layout with the ID magnitude
//        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
//        // Get the magnitude from the current Earthquake object and
//        // set this double on the magnitude TextView
//        magnitudeTextView.setText(Double.toString(earthquake.getMagnitude()));
//
//        // Find the TextView in the list_item.xml layout with the ID location
//        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
//        // Get the location from the current Earthquake object and
//        // set this text on the location TextView
//        locationTextView.setText(earthquake.getLocation());
//
//        // Find the TextView in the list_item.xml layout with the ID date
//        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
//        // Get the date from the current Earthquake object and
//        // set this date on the date TextView
//        long date = earthquake.getDate();
//        dateTextView.setText(dateFormat.format(new Date(date)));
//
//        // Return the whole list item layou
//        return listItemView;
//    }
}
