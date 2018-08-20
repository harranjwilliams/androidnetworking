package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    public static final String LOG_TAG = EarthquakeAdapter.class.getName();
    public static final String OF = "of";
    public final String mNearThe;

    private DateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("h:mm a");
    private DecimalFormat magnitudeFormat = new DecimalFormat("0.0");

    private List<Earthquake> mEarthquakes;
    private Context mContext;

    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for multiple TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        //super(context, 0, earthquakes);

        mEarthquakes = earthquakes;
        mNearThe = context.getString(R.string.near_the);
        Log.d(LOG_TAG, "Earthquakes list size = " + mEarthquakes.size());
        mContext = context;
    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder{

        public final TextView mMagnitudeView;
        public final TextView mLocationOffsetView;
        public final TextView mLocationPrimaryView;
        public final TextView mDateView;
        public final TextView mTimeView;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);

            mMagnitudeView = itemView.findViewById(R.id.magnitude);
            mLocationOffsetView = itemView.findViewById(R.id.location_offset);
            mLocationPrimaryView = itemView.findViewById(R.id.location_primary);
            mDateView = itemView.findViewById(R.id.date);
            mTimeView = itemView.findViewById(R.id.time);
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

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {

        Earthquake earthquake = mEarthquakes.get(position);

        String magnitude = magnitudeFormat.format(earthquake.getMagnitude());
        String location = earthquake.getLocation();

        String offsetLocation = mNearThe;
        String primaryLocation = location;
        if (location.contains(OF)) {
            // The offset location is provided.  Get it and set it to the offset location view
            int indexLocation = location.indexOf(OF);
            int endIndex = indexLocation + OF.length();
            offsetLocation = location.substring(0, endIndex);
            primaryLocation = location.substring(endIndex);
        }

        Date dateTime = new Date(earthquake.getDate());
        String date = dateFormat.format(dateTime);
        String time = timeFormat.format(dateTime);

        Log.d(LOG_TAG, "onBindViewHolder: Position = " + position + " Magnitude = " + magnitude + " Location = " + location + " Date = " + date);

        GradientDrawable magDrawable = (GradientDrawable) holder.mMagnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(mContext, earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magDrawable.setColor(magnitudeColor);

        holder.mMagnitudeView.setText(magnitude);
        holder.mLocationOffsetView.setText(offsetLocation);
        holder.mLocationPrimaryView.setText(primaryLocation);
        holder.mDateView.setText(date);
        holder.mTimeView.setText(time);
    }

    @Override
    public int getItemCount() {
        if (mEarthquakes == null) {
            Log.d(LOG_TAG, "getItemCount: earthquake size = null");
            return 0;
        }
        Log.d(LOG_TAG, "getItemCount: earthquake size = " + mEarthquakes.size());
        return mEarthquakes.size();
    }

    public void setEarthQuakeData(List<Earthquake> earthquakes) {
        mEarthquakes = earthquakes;
        notifyDataSetChanged();
    }

    private int getMagnitudeColor(Context context, double magnitude) {
        switch ((int) Math.floor(magnitude)) {
            case 0: {}
            case 1: { return ContextCompat.getColor(context, R.color.magnitude1); }
            case 2: { return ContextCompat.getColor(context, R.color.magnitude2); }
            case 3: { return ContextCompat.getColor(context, R.color.magnitude3); }
            case 4: { return ContextCompat.getColor(context, R.color.magnitude4); }
            case 5: { return ContextCompat.getColor(context, R.color.magnitude5); }
            case 6: { return ContextCompat.getColor(context, R.color.magnitude6); }
            case 7: { return ContextCompat.getColor(context, R.color.magnitude7); }
            case 8: { return ContextCompat.getColor(context, R.color.magnitude8); }
            case 9: { return ContextCompat.getColor(context, R.color.magnitude9); }
            default: { return ContextCompat.getColor(context, R.color.magnitude10); }
        }
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
